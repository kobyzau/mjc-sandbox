package com.epam.mjc.sandbox.handler.update.callback;

import com.epam.mjc.sandbox.bots.SandboxBot;
import com.epam.mjc.sandbox.entity.Toy;
import com.epam.mjc.sandbox.entity.dto.PayMenuDto;
import com.epam.mjc.sandbox.entity.dto.SerializableInlineType;
import com.epam.mjc.sandbox.service.ToyService;
import com.epam.mjc.sandbox.util.StringUtil;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class PaymentMenuUpdateHandler extends CallbackUpdateHandler<PayMenuDto> {

  @Autowired private SandboxBot bot;
  @Autowired private ToyService toyService;

  @Value("${bot.payment.token}")
  private String paymentToken;

  @Override
  protected Class<PayMenuDto> getDtoType() {
    return PayMenuDto.class;
  }

  @Override
  protected SerializableInlineType getSerializableType() {
    return SerializableInlineType.PAY_MENU;
  }

  @Override
  protected void handleCallback(Update update, PayMenuDto dto) throws TelegramApiException {

    CallbackQuery callbackQuery = update.getCallbackQuery();
    Message message = callbackQuery.getMessage();
    bot.execute(AnswerCallbackQuery.builder().callbackQueryId(callbackQuery.getId()).build());
    Random random = new Random();
    List<Toy> toys = toyService.getToys();
    Toy toy = toys.get(Math.abs(random.nextInt()) % toys.size());
    boolean needName = random.nextBoolean();
    boolean needEmail = random.nextBoolean();
    bot.execute(
        SendInvoice.builder()
            .title(toy.getTitle())
            .description(toy.getDescription())
            .payload("toy:" + toy.getId())
            .photoUrl(toy.getPhotoUrl())
            .photoHeight(300)
            .photoWidth(300)
            .startParameter("startParam")
            .providerToken(paymentToken)
            .currency("RUB")
            .needEmail(needEmail)
            .needName(needName)
            .maxTipAmount(2000)
            .suggestedTipAmount(1000)
            .price(LabeledPrice.builder().label("Цена товара").amount(toy.getPrice()).build())
            .price(
                LabeledPrice.builder().label("Доставка").amount(random.nextInt(10) * 100).build())
            .price(LabeledPrice.builder().label("Налог (10%)").amount(toy.getPrice() / 10).build())
            .chatId(message.getChatId().toString())
            .replyMarkup(
                InlineKeyboardMarkup.builder()
                    .keyboardRow(
                        Collections.singletonList(
                            InlineKeyboardButton.builder().text("Купи игрушку!").pay(true).build()))
                    .keyboardRow(
                        Collections.singletonList(
                            InlineKeyboardButton.builder()
                                .text("Другой товар")
                                .callbackData(StringUtil.serialize(new PayMenuDto()))
                                .build()))
                    .build())
            .build());
  }
}
