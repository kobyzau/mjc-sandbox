package com.epam.mjc.sandbox.handler.update;

import com.epam.mjc.sandbox.entity.Command;
import com.epam.mjc.sandbox.entity.dto.ParsedCommandDto;
import com.epam.mjc.sandbox.handler.command.CommandHandler;
import com.epam.mjc.sandbox.handler.command.CommandHandlerFactory;
import com.epam.mjc.sandbox.parser.CommandParser;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class CommandUpdateHandler implements UpdateHandler {
  @Autowired private CommandParser commandParser;
  @Autowired private CommandHandlerFactory commandHandlerFactory;

  @Override
  public boolean handleUpdate(Update update) throws TelegramApiException {
    if (!update.hasMessage()) {
      return false;
    }
    Message message = update.getMessage();
    if (!message.hasText()) {
      return false;
    }
    String text = message.getText();
    Optional<ParsedCommandDto> command = commandParser.parseCommand(text);
    if (!command.isPresent()) {
      return false;
    }
    handleCommand(update, command.get().getCommand(), command.get().getText());
    return true;
  }

  private void handleCommand(Update update, Command command, String text)
      throws TelegramApiException {
    CommandHandler commandHandler = commandHandlerFactory.getHandler(command);
    commandHandler.handleCommand(update.getMessage(), text);
  }

  @Override
  public UpdateHandlerStage getStage() {
    return UpdateHandlerStage.COMMAND;
  }
}
