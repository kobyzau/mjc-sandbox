package com.epam.mjc.sandbox.client;

import com.epam.mjc.sandbox.entity.youtube.SearchResultListResponse;
import feign.RequestInterceptor;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    value = "youtube",
    url = "https://www.googleapis.com/youtube/v3",
    configuration = YoutubeClient.Configuration.class)
public interface YoutubeClient {

  @GetMapping("/search?part=snippet&type=video")
  Optional<SearchResultListResponse> searchVideoByChannelId(
      @RequestParam("channelId") String id,
      @RequestParam("q") String searchQuery,
      @RequestParam("pageToken") String pageToken);

  class Configuration {
    @Bean
    public RequestInterceptor tokenInterceptor(
        @Value("${api.youtube.token}") String token,
        @Value("${api.youtube.pageSize}") int pageSize) {
      return template -> template.query("key", token).query("maxResults", String.valueOf(pageSize));
    }
  }
}
