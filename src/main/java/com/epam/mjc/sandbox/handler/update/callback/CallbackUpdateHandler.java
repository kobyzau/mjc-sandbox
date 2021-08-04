package com.epam.mjc.sandbox.handler.update.callback;

import com.epam.mjc.sandbox.entity.dto.SerializableInlineObject;
import com.epam.mjc.sandbox.entity.dto.SerializableInlineType;
import com.epam.mjc.sandbox.handler.update.UpdateHandler;
import com.epam.mjc.sandbox.handler.update.UpdateHandlerStage;
import com.epam.mjc.sandbox.util.StringUtil;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class CallbackUpdateHandler<T extends SerializableInlineObject>
    implements UpdateHandler {

  protected abstract Class<T> getDtoType();

  protected abstract SerializableInlineType getSerializableType();

  protected abstract void handleCallback(Update update, T dto) throws TelegramApiException;

  @Override
  public boolean handleUpdate(Update update) throws TelegramApiException {
    CallbackQuery callbackQuery = update.getCallbackQuery();
    if (callbackQuery == null || callbackQuery.getMessage() == null) {
      return false;
    }
    String data = callbackQuery.getData();
    Optional<T> dto = StringUtil.deserialize(data, getDtoType());
    if (!dto.isPresent() || dto.get().getIndex() != getSerializableType().getIndex()) {
      return false;
    }
    log.info("Found callback {}", getSerializableType());
    handleCallback(update, dto.get());
    return true;
  }

  @Override
  public UpdateHandlerStage getStage() {
    return UpdateHandlerStage.CALLBACK;
  }
}
