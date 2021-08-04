package com.epam.mjc.sandbox.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ExpectedMessageAction {

  private ExpectedMessageActionType action;
  private long gameId;

  public ExpectedMessageAction(ExpectedMessageActionType action, long gameId) {
    this.action = action;
    this.gameId = gameId;
  }
}
