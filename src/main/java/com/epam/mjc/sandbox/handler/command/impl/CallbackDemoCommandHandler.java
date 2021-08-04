package com.epam.mjc.sandbox.handler.command.impl;

import com.epam.mjc.sandbox.entity.dto.AlertDto;
import com.epam.mjc.sandbox.entity.dto.RandomMessageDto;
import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.entity.Command;
import com.epam.mjc.sandbox.handler.command.CommandHandler;
import com.epam.mjc.sandbox.util.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class CallbackDemoCommandHandler implements CommandHandler {

  @Autowired private SandboxBot sandboxBot;

  @Override
  public void handleCommand(Message message, String text) throws TelegramApiException {
    List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
    buttons.add(
        Arrays.asList(
            InlineKeyboardButton.builder().text("External Link").url("www.google.com").build(),
            InlineKeyboardButton.builder()
                .text("Internal Link")
                .url("https://t.me/BotFather")
                .build()));
    buttons.add(
        Collections.singletonList(
            InlineKeyboardButton.builder()
                .text("Inline query")
                .switchInlineQueryCurrentChat("Concurrent")
                .build()));
    buttons.add(
        Arrays.asList(
            InlineKeyboardButton.builder()
                .text("Alert")
                .callbackData(StringUtil.serialize(new AlertDto("Message 1", true)))
                .build(),
            InlineKeyboardButton.builder()
                .text("Simple Alert")
                .callbackData(StringUtil.serialize(new AlertDto("Message 2", false)))
                .build()));
    buttons.add(
        Collections.singletonList(
            InlineKeyboardButton.builder()
                .text("Edit Message")
                .callbackData(StringUtil.serialize(new RandomMessageDto()))
                .build()));

    sandboxBot.execute(
        SendMessage.builder()
            .text("This is Demo of Telegram Bot.\nPlease choose feature for demonstration")
            .parseMode("html")
            .chatId(message.getChatId().toString())
            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
            .build());
  }

  @Override
  public Command getCommand() {
    return Command.CALLBACK_DEMO;
  }
}
