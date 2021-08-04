package com.epam.mjc.sandbox.handler.update;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface UpdateHandler {

    boolean handleUpdate(Update update) throws TelegramApiException;

    UpdateHandlerStage getStage();
}
