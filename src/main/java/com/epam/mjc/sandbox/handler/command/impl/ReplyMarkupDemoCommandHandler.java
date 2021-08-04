package com.epam.mjc.sandbox.handler.command.impl;

import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.entity.Command;
import com.epam.mjc.sandbox.handler.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonPollType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ReplyMarkupDemoCommandHandler implements CommandHandler {
  @Autowired private SandboxBot bot;

  @Override
  public void handleCommand(Message message, String text) throws TelegramApiException {

    KeyboardRow rowText = new KeyboardRow();
    KeyboardRow rowRequest = new KeyboardRow();
    rowText.add("Text 1");
    rowText.add("Text 2");
    rowRequest.add(KeyboardButton.builder().text("Contact").requestContact(true).build());
    rowRequest.add(KeyboardButton.builder().text("Location").requestLocation(true).build());
    rowRequest.add(
        KeyboardButton.builder().text("Poll").requestPoll(new KeyboardButtonPollType()).build());

    bot.execute(
        SendMessage.builder()
            .text("This message contains ReplyKeyboardMarkup")
            .chatId(message.getChatId().toString())
            .replyMarkup(
                ReplyKeyboardMarkup.builder().keyboardRow(rowText).keyboardRow(rowRequest).build())
            .build());
  }

  @Override
  public Command getCommand() {
    return Command.REPLY_MARKUP_DEMO;
  }
}
