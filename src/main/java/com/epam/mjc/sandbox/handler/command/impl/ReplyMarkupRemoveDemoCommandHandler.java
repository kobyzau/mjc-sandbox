package com.epam.mjc.sandbox.handler.command.impl;

import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.entity.Command;
import com.epam.mjc.sandbox.handler.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ReplyMarkupRemoveDemoCommandHandler implements CommandHandler {
  @Autowired private SandboxBot bot;

  @Override
  public void handleCommand(Message message, String text) throws TelegramApiException {

    bot.execute(
        SendMessage.builder()
            .text("This message contains ReplyKeyboardRemove")
            .chatId(message.getChatId().toString())
            .replyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build())
            .build());
  }

  @Override
  public Command getCommand() {
    return Command.REPLY_MARKUP_REMOVE_DEMO;
  }
}
