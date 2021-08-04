package com.epam.mjc.sandbox.parser;

import com.epam.mjc.sandbox.entity.dto.ParsedCommandDto;
import com.epam.mjc.sandbox.entity.Command;
import com.epam.mjc.sandbox.util.StringUtil;
import java.util.Optional;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseCommandParser implements CommandParser {

  private final String PREFIX_FOR_COMMAND = "/";
  private final String DELIMITER_COMMAND_BOTNAME = "@";

  @Value("${bot.sandbox.username}")
  private String botName;

  public BaseCommandParser() {}

  public BaseCommandParser(String botName) {
    this.botName = botName;
  }

  @Override
  public Optional<ParsedCommandDto> parseCommand(String message) {
    if (StringUtil.isBlank(message)) {
      return Optional.empty();
    }
    String trimText = StringUtil.trim(message);
    Pair<String, String> commandAndText = getDelimitedCommandFromText(trimText);
    if (isCommand(commandAndText.getKey())) {
      if (isCommandForMe(commandAndText.getKey())) {
        String commandForParse = cutCommandFromFullText(commandAndText.getKey());
        Optional<Command> command = Command.parseCommand(commandForParse);
        return command.map(c -> new ParsedCommandDto(c, commandAndText.getValue()));
      } else {
        return Optional.empty();
      }
    }
    return Optional.empty();
  }

  private String cutCommandFromFullText(String text) {
    return text.contains(DELIMITER_COMMAND_BOTNAME)
        ? text.substring(1, text.indexOf(DELIMITER_COMMAND_BOTNAME))
        : text.substring(1);
  }

  private Pair<String, String> getDelimitedCommandFromText(String trimText) {
    Pair<String, String> commandText;

    if (trimText.contains(" ")) {
      int indexOfSpace = trimText.indexOf(" ");
      commandText =
          new Pair<>(trimText.substring(0, indexOfSpace), trimText.substring(indexOfSpace + 1));
    } else commandText = new Pair<>(trimText, "");
    return commandText;
  }

  private boolean isCommandForMe(String command) {
    if (command.contains(DELIMITER_COMMAND_BOTNAME)) {
      String botNameForEqual = command.substring(command.indexOf(DELIMITER_COMMAND_BOTNAME) + 1);
      return botName.equals(botNameForEqual);
    }
    return true;
  }

  private boolean isCommand(String text) {
    return text.startsWith(PREFIX_FOR_COMMAND);
  }
}
