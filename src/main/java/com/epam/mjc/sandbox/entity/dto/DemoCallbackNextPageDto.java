package com.epam.mjc.sandbox.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemoCallbackNextPageDto extends SerializableInlineObject {
  @JsonProperty("p")
  private int page;

  public DemoCallbackNextPageDto() {
    super(SerializableInlineType.NEXT_PAGE);
  }

  public DemoCallbackNextPageDto(int page) {
    this();
    this.page = page;
  }
}
