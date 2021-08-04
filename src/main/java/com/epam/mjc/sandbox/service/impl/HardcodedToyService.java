package com.epam.mjc.sandbox.service.impl;

import com.epam.mjc.sandbox.entity.Toy;
import com.epam.mjc.sandbox.service.ToyService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class HardcodedToyService implements ToyService {
  private final List<Toy> toys = new ArrayList<>();

  @PostConstruct
  private void init() {
    long id = 1;
    toys.add(
        new Toy(
            id++,
            "Кот в байке",
            "Отличный кот в байке. Отличная игрушка для всех. Купи, не пожалеешь",
            "https://avatars.mds.yandex.net/get-mpic/1970506/img_id3612455588033939281.jpeg/13hq",
            15000));
    toys.add(
        new Toy(
            id++,
            "Лиса",
            "Лиса из Minecraft. Хочешь, чтобы все завидовали тебе? Тогда купи эту замечательную игрушку",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgBrvxL5zzzGioBze-tAhOIpJ7SWr8R1aIVry_fTjvXbFEyhchLX8A94V-8xtOeC5JZGY&usqp=CAU",
            9000));
    toys.add(
        new Toy(
            id++,
            "Кукла",
            "Это просто кукла. Самая лучшая игрушка для всех возрастов. Не каждый может похвастаться такой игрушкой",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTL9H4uH4y19r1aOVlpVKFTftwHSk0dNZ2U72T0xR4YFDnesT5OwpzpnHJfvWNfwqoN0Hg&usqp=CAU",
            18000));
    toys.add(
        new Toy(
            id++,
            "Авокадо",
            "Авокадо среднего размера. Мягкая игрушка для детей любого возраста.",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQSwsBHu8oHd810pWMxggxb50JP2Mf3ppLMBA&usqp=CAU",
            11000));
  }

  @Override
  public List<Toy> getToys() {
    return Collections.unmodifiableList(toys);
  }
}
