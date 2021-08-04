package com.epam.mjc.sandbox.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RandomMessageDto extends SerializableInlineObject {

  @JsonProperty("id")
  private int id;

  public RandomMessageDto() {
    super(SerializableInlineType.RANDOM_MESSAGE);
  }

  public RandomMessageDto(int id) {
    this();
    this.id = id;
  }
}
