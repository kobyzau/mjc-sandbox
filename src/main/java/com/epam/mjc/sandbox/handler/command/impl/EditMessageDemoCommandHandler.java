package com.epam.mjc.sandbox.handler.command.impl;

import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.entity.Command;
import com.epam.mjc.sandbox.handler.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class EditMessageDemoCommandHandler implements CommandHandler {
  @Autowired private SandboxBot bot;

  @Override
  public void handleCommand(Message message, String text) throws TelegramApiException {
    Long chatId = message.getChatId();
    Message newMessage =
        bot.execute(
            SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text("This is original message")
                .build());
    try {
      Thread.sleep(1500);
    } catch (Exception ignored) {
    }
    bot.execute(
        EditMessageText.builder()
            .chatId(String.valueOf(chatId))
            .messageId(newMessage.getMessageId())
            .text("This is <b>updated</b> message")
            .parseMode("html")
            .build());
    bot.execute(SendChatAction.builder().chatId(String.valueOf(chatId)).action("typing").build());
    try {
      Thread.sleep(1500);
    } catch (Exception ignored) {
    }
    bot.execute(
        SendSticker.builder()
            .chatId(String.valueOf(chatId))
            .sticker(
                new InputFile(
                    "CAACAgIAAxkBAAICqF5FNXXx247rW2QUWxnL8uLNoEivAAIEAQACVp29Ct4E0XpmZvdsGAQ"))
            .build());

    bot.execute(
        SendChatAction.builder().chatId(String.valueOf(chatId)).action("upload_photo").build());
    try {
      Thread.sleep(1500);
    } catch (Exception ignored) {
    }

    bot.execute(
        SendPhoto.builder()
            .chatId(String.valueOf(chatId))
            .photo(
                new InputFile(
                    "AgACAgIAAxkBAAOoYQl0WI7Mdbj1qQJrRiKZpLBPZS8AAu-0MRuO01BItvZz8wh_WlgBAAMCAANtAAMgBA"))
            .build());
  }

  @Override
  public Command getCommand() {
    return Command.EDIT_MESSAGE_DEMO;
  }
}
