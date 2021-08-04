package com.epam.mjc.sandbox.handler.update;

import com.epam.mjc.sandbox.bots.SandboxBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class PreCheckoutQueryUpdateHandler implements UpdateHandler {
  @Autowired private SandboxBot bot;

  @Override
  public boolean handleUpdate(Update update) throws TelegramApiException {
    if (update.hasPreCheckoutQuery()) {
      PreCheckoutQuery preCheckoutQuery = update.getPreCheckoutQuery();
      String id = preCheckoutQuery.getId();
      boolean isOk = preCheckoutQuery.getTotalAmount() < 15000;
      bot.execute(
          AnswerPreCheckoutQuery.builder()
              .ok(isOk)
              .errorMessage(
                  isOk
                      ? null
                      : "Извините, данный товар отсутсвует на складе. Пожалуйста выберите другой товар")
              .preCheckoutQueryId(id)
              .build());
      return true;
    }
    return false;
  }

  @Override
  public UpdateHandlerStage getStage() {
    return UpdateHandlerStage.PAYMENT;
  }
}
