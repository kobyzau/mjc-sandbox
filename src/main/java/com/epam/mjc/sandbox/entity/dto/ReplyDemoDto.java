package com.epam.mjc.sandbox.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDemoDto extends SerializableInlineObject {
  @JsonProperty("s")
  private boolean selective;

  @JsonProperty("o")
  private boolean oneTime;

  @JsonProperty("r")
  private boolean resize;

  public ReplyDemoDto() {
    super(SerializableInlineType.REPLY_DEMO);
  }

  public ReplyDemoDto(boolean selective, boolean oneTime, boolean resize) {
    this();
    this.selective = selective;
    this.oneTime = oneTime;
    this.resize = resize;
  }
}
