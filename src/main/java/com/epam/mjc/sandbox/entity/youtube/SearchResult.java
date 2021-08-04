package com.epam.mjc.sandbox.entity.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {
  private SearchResultId id;
  private SearchResultSnippet snippet;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SearchResultId {
    private String kind;
    private String videoId;
    private String playlistId;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SearchResultSnippet {
    private String title;
    private SearchResultThumbnails thumbnails;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SearchResultThumbnails {
    @JsonProperty("default")
    private SearchResultThumbnail defaultThumbnail;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SearchResultThumbnail {
    private String url;
    private int width;
    private int height;
  }
}
