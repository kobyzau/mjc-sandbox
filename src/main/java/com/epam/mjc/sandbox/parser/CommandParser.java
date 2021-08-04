package com.epam.mjc.sandbox.parser;

import com.epam.mjc.sandbox.entity.dto.ParsedCommandDto;
import java.util.Optional;

public interface CommandParser {

  Optional<ParsedCommandDto> parseCommand(String message);
}
