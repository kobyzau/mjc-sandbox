package com.epam.mjc.sandbox.handler.update;

import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.entity.ReplyKeyboardButton;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.epam.mjc.sandbox.handler.update.UpdateHandlerStage.REPLY_BUTTON;

@Component
public class ReplyKeyboardButtonUpdateHandler implements UpdateHandler {
  @Autowired private SandboxBot sandboxBot;

  @Override
  public boolean handleUpdate(Update update) throws TelegramApiException {
    if (!update.hasMessage()) {
      return false;
    }
    Message message = update.getMessage();
    if (!message.hasText()) {
      return false;
    }
    String messageText = message.getText();
    Optional<ReplyKeyboardButton> keyboardButton = ReplyKeyboardButton.parse(messageText);
    if (keyboardButton.isPresent()) {
      String chatId = message.getChatId().toString();
      switch (keyboardButton.get()) {
        case HOME:
          sandboxBot.execute(SendMessage.builder().chatId(chatId).text("Home action").build());
          break;
        case SETTING:
          sandboxBot.execute(SendMessage.builder().chatId(chatId).text("Setting action").build());
          break;
        case INFO:
          sandboxBot.execute(SendMessage.builder().chatId(chatId).text("Info action").build());
          break;
        case STATISTIC:
          sandboxBot.execute(SendMessage.builder().chatId(chatId).text("Statistic action").build());
          break;
      }
      return true;
    }
    return false;
  }

  @Override
  public UpdateHandlerStage getStage() {
    return REPLY_BUTTON;
  }
}
