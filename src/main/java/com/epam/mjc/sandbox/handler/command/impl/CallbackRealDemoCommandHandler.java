package com.epam.mjc.sandbox.handler.command.impl;

import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.entity.Command;
import com.epam.mjc.sandbox.entity.dto.AlertDto;
import com.epam.mjc.sandbox.entity.dto.DemoCallbackNextPageDto;
import com.epam.mjc.sandbox.handler.command.CommandHandler;
import com.epam.mjc.sandbox.handler.update.callback.DemoCallbackNextPageUpdateHandler;
import com.epam.mjc.sandbox.util.StringUtil;
import java.util.ArrayList;
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
public class CallbackRealDemoCommandHandler implements CommandHandler {

  @Autowired private SandboxBot bot;

  @Override
  public void handleCommand(Message message, String text) throws TelegramApiException {
    List<InlineKeyboardButton> pagination = new ArrayList<>();
    pagination.add(
        InlineKeyboardButton.builder()
            .text(String.valueOf(1))
            .callbackData(
                StringUtil.serialize(
                    new AlertDto(
                        "Page 1 from " + DemoCallbackNextPageUpdateHandler.MAX_PAGE, false)))
            .build());
    pagination.add(
        InlineKeyboardButton.builder()
            .text("▶")
            .callbackData(StringUtil.serialize(new DemoCallbackNextPageDto(2)))
            .build());
    bot.execute(
        SendMessage.builder()
            .text(
                "<b>Page 1</b>\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry."
                    + " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an"
                    + " unknown printer took a galley of type and scrambled it to make a type specimen book.")
            .parseMode("html")
            .chatId(message.getChatId().toString())
            .replyMarkup(
                InlineKeyboardMarkup.builder()
                    .keyboardRow(pagination)
                    .keyboardRow(
                        Collections.singletonList(
                            InlineKeyboardButton.builder()
                                .text("Open \uD83D\uDC46")
                                .callbackData(
                                    StringUtil.serialize(
                                        new AlertDto("Not Implemented \uD83D\uDE22", true)))
                                .build()))
                    .build())
            .build());
  }

  @Override
  public Command getCommand() {
    return Command.CALLBACK_REAL_DEMO;
  }
}
