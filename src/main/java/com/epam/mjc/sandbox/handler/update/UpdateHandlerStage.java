package com.epam.mjc.sandbox.handler.update;

public enum UpdateHandlerStage {
  CALLBACK,
  INLINE,
  PAYMENT,
  COMMAND,
  REPLY_BUTTON
  ;

  public int getOrder() {
    return ordinal();
  }
}
