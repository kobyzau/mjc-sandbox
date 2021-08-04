package com.epam.mjc.sandbox.entity.dto;

public enum SerializableInlineType {
  ALERT(0),
  RANDOM_MESSAGE(1),
  REPLY_DEMO(2),
  REPLY_REMOVE_DEMO(3),
  FORCE_REPLY_DEMO(4),
  PAY_MENU(5),
  NEXT_PAGE(6),
  ;

  private final int index;

  SerializableInlineType(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }
}
