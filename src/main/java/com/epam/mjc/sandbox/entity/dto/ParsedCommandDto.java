package com.epam.mjc.sandbox.entity.dto;


import com.epam.mjc.sandbox.entity.Command;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ParsedCommandDto {
    private final Command command;
    private final String text;
}