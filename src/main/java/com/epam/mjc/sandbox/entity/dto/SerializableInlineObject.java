package com.epam.mjc.sandbox.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public abstract class SerializableInlineObject {

  @JsonProperty("i")
  private int index;

  public SerializableInlineObject(SerializableInlineType type) {
    this.index = type.getIndex();
  }

  public void setIndex(int i) {
    this.index = i;
  }

  public int getIndex() {
    return index;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SerializableInlineObject that = (SerializableInlineObject) o;
    return index == that.index;
  }

  @Override
  public int hashCode() {
    return Objects.hash( index);
  }
}
