package com.epam.mjc.sandbox.handler.command.impl;

import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.entity.Command;
import com.epam.mjc.sandbox.entity.ReplyKeyboardButton;
import com.epam.mjc.sandbox.handler.command.CommandHandler;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ReplyMarkupRealDemoCommandHandler implements CommandHandler {
  @Autowired private SandboxBot bot;

  @Override
  public void handleCommand(Message message, String text) throws TelegramApiException {

    bot.execute(
        SendMessage.builder()
            .text("Welcome to the Bot!\nPlease choose some operation")
            .chatId(message.getChatId().toString())
            .replyMarkup(
                ReplyKeyboardMarkup.builder()
                    .resizeKeyboard(true)
                    .keyboardRow(
                        new KeyboardRow(
                            Arrays.asList(
                                KeyboardButton.builder()
                                    .text(ReplyKeyboardButton.HOME.getLabel())
                                    .build(),
                                KeyboardButton.builder()
                                    .text(ReplyKeyboardButton.SETTING.getLabel())
                                    .build())))
                    .keyboardRow(
                        new KeyboardRow(
                            Arrays.asList(
                                KeyboardButton.builder()
                                    .text(ReplyKeyboardButton.INFO.getLabel())
                                    .build(),
                                KeyboardButton.builder()
                                    .text(ReplyKeyboardButton.STATISTIC.getLabel())
                                    .build())))
                    .build())
            .build());
  }

  @Override
  public Command getCommand() {
    return Command.REPLY_MARKUP_REAL_DEMO;
  }
}
