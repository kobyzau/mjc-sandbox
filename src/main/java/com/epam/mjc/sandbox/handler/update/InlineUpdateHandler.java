package com.epam.mjc.sandbox.handler.update;

import com.epam.mjc.sandbox.entity.youtube.SearchResult;
import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.client.YoutubeClient;
import com.epam.mjc.sandbox.entity.youtube.SearchResultListResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedGif;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class InlineUpdateHandler implements UpdateHandler {

  @Autowired private SandboxBot bot;
  @Autowired private YoutubeClient youtubeClient;

  @Value("${mjc.youtube.channelId}")
  private String mjcChannelId;

  @Value("${mjc.thumbnail.url}")
  private String mjcThumbnail;

  private static final int DEFAULT_THUMBNAIL_SIZE = 100;
  private static final int CACHE_TIME = 10;

  @Override
  public boolean handleUpdate(Update update) throws TelegramApiException {
    if (!update.hasInlineQuery()) {
      return false;
    }
    InlineQuery inlineQuery = update.getInlineQuery();
    String query = "*".equals(inlineQuery.getQuery()) ? "" : inlineQuery.getQuery();
    String offset = inlineQuery.getOffset();
    List<InlineQueryResult> inlineQueryResults = new ArrayList<>();
    Optional<SearchResultListResponse> searchResultListResponse =
        youtubeClient.searchVideoByChannelId(mjcChannelId, query, offset);
    List<SearchResult> searchResults =
        searchResultListResponse
            .map(SearchResultListResponse::getItems)
            .orElse(Collections.emptyList())
            .stream()
            .filter(i -> i.getSnippet() != null)
            .collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(searchResults)) {
      for (SearchResult item : searchResults) {
        Optional<SearchResult.SearchResultThumbnail> thumbnail =
            Optional.of(item.getSnippet())
                .map(SearchResult.SearchResultSnippet::getThumbnails)
                .map(SearchResult.SearchResultThumbnails::getDefaultThumbnail);
        inlineQueryResults.add(
            InlineQueryResultArticle.builder()
                .id("Youtube:" + item.getId().getVideoId())
                .title(item.getSnippet().getTitle())
                .hideUrl(false)
                .url("https://www.youtube.com/watch?v=" + item.getId().getVideoId())
                .thumbUrl(
                    thumbnail.map(SearchResult.SearchResultThumbnail::getUrl).orElse(mjcThumbnail))
                .thumbWidth(
                    thumbnail
                        .map(SearchResult.SearchResultThumbnail::getWidth)
                        .orElse(DEFAULT_THUMBNAIL_SIZE))
                .thumbHeight(
                    thumbnail
                        .map(SearchResult.SearchResultThumbnail::getHeight)
                        .orElse(DEFAULT_THUMBNAIL_SIZE))
                .inputMessageContent(
                    InputTextMessageContent.builder()
                        .messageText("https://www.youtube.com/watch?v=" + item.getId().getVideoId())
                        .build())
                .build());
      }
    } else {
      inlineQueryResults.add(
          InlineQueryResultCachedGif.builder()
              .id("Gif:1")
              .title("Not Found")
              .gifFileId("CgACAgQAAxkBAAOpYQl0dmiurrAJQa0DxmU-sK0HOx8AAvUCAALsxuRSQH1sV85aCZ0gBA")
              .build());
    }
    bot.execute(
        AnswerInlineQuery.builder()
            .inlineQueryId(inlineQuery.getId())
            .nextOffset(
                searchResultListResponse
                    .map(SearchResultListResponse::getNextPageToken)
                    .orElse(null))
            .results(inlineQueryResults)
            .cacheTime(CACHE_TIME)
            .build());
    return true;
  }

  @Override
  public UpdateHandlerStage getStage() {
    return UpdateHandlerStage.INLINE;
  }
}
