package com.epam.mjc.sandbox.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForceReplyDemoDto extends SerializableInlineObject {
  @JsonProperty("f")
  private boolean force;

  public ForceReplyDemoDto() {
    super(SerializableInlineType.FORCE_REPLY_DEMO);
  }

  public ForceReplyDemoDto(boolean force) {
    this();
    this.force = force;
  }
}
