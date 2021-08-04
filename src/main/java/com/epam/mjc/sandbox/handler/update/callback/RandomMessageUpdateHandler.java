package com.epam.mjc.sandbox.handler.update.callback;

import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.entity.dto.RandomMessageDto;
import com.epam.mjc.sandbox.entity.dto.SerializableInlineType;
import com.epam.mjc.sandbox.util.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class RandomMessageUpdateHandler extends CallbackUpdateHandler<RandomMessageDto> {
  @Autowired private SandboxBot bot;

  private final List<String> textList = new ArrayList<>();

  @Override
  protected Class<RandomMessageDto> getDtoType() {
    return RandomMessageDto.class;
  }

  @Override
  protected SerializableInlineType getSerializableType() {
    return SerializableInlineType.RANDOM_MESSAGE;
  }

  @PostConstruct
  private void init() {
    textList.add(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur a urna ut nulla elementum dignissim in sed nulla. Fusce dapibus eros urna, in sollicitudin mi porta pretium. Pellentesque a libero sed lacus fermentum ornare. Nulla facilisi. Vivamus eros mauris, ultricies id commodo blandit, efficitur vitae nunc. Nam vel interdum mauris, vel blandit magna. Nullam ac dolor id nisl lacinia condimentum id in est. Nam quis mi nec risus feugiat condimentum. ");
    textList.add(
        "Praesent vestibulum efficitur nulla, in dictum mi volutpat quis. Mauris sodales molestie vulputate. Fusce non nunc congue, maximus nulla non, rutrum enim. In faucibus ut quam at dapibus. Nullam dignissim dictum risus a semper. Fusce commodo neque libero, ac iaculis lorem ultrices a. Cras malesuada tellus sem, sit amet scelerisque neque vestibulum sed. Vivamus viverra efficitur ante, id condimentum arcu mattis consectetur. Donec placerat turpis risus, ut mattis lectus mollis at. In bibendum arcu volutpat libero viverra, quis scelerisque diam pellentesque. Praesent tortor eros, pellentesque sed neque sit amet, dapibus porta risus. Sed ultrices finibus neque, et sollicitudin nunc volutpat sit amet. Ut porttitor consequat ultricies. Donec eget erat lacus. Fusce mattis quam sit amet tortor auctor, vitae dignissim purus faucibus. Integer et ex commodo, commodo metus sit amet, efficitur risus. ");
    textList.add(
        "Maecenas in est et augue auctor efficitur. Mauris id dictum massa, vel pretium lectus. Morbi feugiat tortor fermentum, ullamcorper nunc a, sollicitudin justo. Suspendisse vehicula semper rhoncus. Cras nec arcu ut quam vestibulum blandit. Nullam varius fringilla dui at commodo. Nullam eget tortor ac ipsum luctus iaculis vitae non tellus. Quisque pretium neque vitae dolor vulputate venenatis. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur sit amet diam sit amet tortor fringilla posuere. In congue id tortor vitae aliquam. ");
    textList.add(
        "Sed a dolor et leo vehicula sodales. Suspendisse est augue, interdum quis arcu vel, commodo scelerisque eros. Nullam convallis ut metus vel iaculis. Aenean in varius erat, quis scelerisque libero. Mauris elementum at magna sed sodales. Sed consectetur, augue ut fermentum semper, nisi mauris molestie ante, in lobortis massa dolor in velit. Ut sit amet sodales sem. ");
    textList.add(
        "Suspendisse potenti. Sed elementum rhoncus nisl, efficitur imperdiet lectus hendrerit sed. Proin sollicitudin metus ut est posuere, porta commodo quam molestie. Ut aliquet augue dui, id pretium lectus gravida et. Duis eleifend sollicitudin nibh ut lobortis. Quisque in sapien ligula. Nunc pellentesque malesuada risus in dignissim. Pellentesque viverra convallis felis. Maecenas et magna efficitur nulla molestie bibendum. Vestibulum congue urna vel orci hendrerit, blandit dapibus ante suscipit. ");
  }

  @Override
  protected void handleCallback(Update update, RandomMessageDto dto) throws TelegramApiException {
    int id = dto.getId();
    int nextId = id == (textList.size() - 1) ? 0 : id + 1;
    bot.execute(
        EditMessageText.builder()
            .text(textList.get(id))
            .messageId(update.getCallbackQuery().getMessage().getMessageId())
            .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
            .replyMarkup(
                InlineKeyboardMarkup.builder()
                    .keyboardRow(
                        Collections.singletonList(
                            InlineKeyboardButton.builder()
                                .text("Next")
                                .callbackData(StringUtil.serialize(new RandomMessageDto(nextId)))
                                .build()))
                    .build())
            .build());
  }
}
