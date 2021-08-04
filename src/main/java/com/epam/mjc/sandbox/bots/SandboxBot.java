package com.epam.mjc.sandbox.bots;

import com.epam.mjc.sandbox.handler.update.UpdateHandler;
import com.epam.mjc.sandbox.entity.Command;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class SandboxBot extends TelegramLongPollingBot {


  @Value("${bot.sandbox.username}")
  private String username;
  @Value("${bot.sandbox.token}")
  private String token;


  @Autowired private List<UpdateHandler> updateHandlers;

  @PostConstruct
  public void init() {
    updateHandlers =
        updateHandlers.stream()
            .sorted(Comparator.comparingInt(u -> u.getStage().getOrder()))
            .collect(Collectors.toList());
    try {
      TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
      telegramBotsApi.registerBot(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
    setupCommands();
  }

  private void setupCommands() {
    try {
      List<BotCommand> commands =
          Arrays.stream(Command.values())
              .map(c -> BotCommand.builder().command(c.getName()).description(c.getDesc()).build())
              .collect(Collectors.toList());
      execute(SetMyCommands.builder().commands(commands).build());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onUpdateReceived(Update update) {
    for (UpdateHandler updateHandler : updateHandlers) {
      try {
        if (updateHandler.handleUpdate(update)) {
          return;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public String getBotUsername() {
    return username;
  }
}
