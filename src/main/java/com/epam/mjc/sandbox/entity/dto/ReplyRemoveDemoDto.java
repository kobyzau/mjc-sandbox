package com.epam.mjc.sandbox.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyRemoveDemoDto extends SerializableInlineObject {
  @JsonProperty("r")
  private boolean remove;

  public ReplyRemoveDemoDto() {
    super(SerializableInlineType.REPLY_REMOVE_DEMO);
  }

  public ReplyRemoveDemoDto(boolean remove) {
    this();
    this.remove = remove;
  }
}
