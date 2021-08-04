package com.epam.mjc.sandbox.entity;

import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReplyKeyboardButton {
  HOME("Home \uD83C\uDFE1"),
  SETTING("Settings \uD83D\uDEE0"),
  INFO("Info \uD83D\uDCD6"),
  STATISTIC("Statistic \uD83D\uDCCA");

  private final String label;

  public static Optional<ReplyKeyboardButton> parse(String name) {
    return Arrays.stream(values())
        .filter(b -> b.name().equalsIgnoreCase(name) || b.label.equalsIgnoreCase(name))
        .findFirst();
  }
}
