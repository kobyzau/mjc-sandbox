package com.epam.mjc.sandbox.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Toy {
  private long id;
  private String title;
  private String description;
  private String photoUrl;
  private int price;
}
