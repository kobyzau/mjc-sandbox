package com.epam.mjc.sandbox.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertDto extends SerializableInlineObject {
  @JsonProperty("m")
  private String message;

  @JsonProperty("f")
  private boolean fullMode;

  public AlertDto() {
    super(SerializableInlineType.ALERT);
  }

  public AlertDto(String message, boolean fullMode) {
    this();
    this.message = message;
    this.fullMode = fullMode;
  }
}
