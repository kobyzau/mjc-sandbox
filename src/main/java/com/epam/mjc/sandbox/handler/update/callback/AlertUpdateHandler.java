package com.epam.mjc.sandbox.handler.update.callback;

import com.epam.mjc.sandbox.entity.dto.AlertDto;
import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.entity.dto.SerializableInlineType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AlertUpdateHandler extends CallbackUpdateHandler<AlertDto> {
  @Autowired private SandboxBot bot;

  @Override
  protected Class<AlertDto> getDtoType() {
    return AlertDto.class;
  }

  @Override
  protected SerializableInlineType getSerializableType() {
    return SerializableInlineType.ALERT;
  }

  @Override
  protected void handleCallback(Update update, AlertDto dto) throws TelegramApiException {
    bot.execute(
        AnswerCallbackQuery.builder()
            .cacheTime(10)
            .text(dto.getMessage())
            .showAlert(dto.isFullMode())
            .callbackQueryId(update.getCallbackQuery().getId())
            .build());
  }
}
