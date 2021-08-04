package com.epam.mjc.sandbox.entity.youtube;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultListResponse {
  private List<SearchResult> items;
  private String nextPageToken;
}
