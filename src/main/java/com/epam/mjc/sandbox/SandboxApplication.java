package com.epam.mjc.sandbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableFeignClients
public class SandboxApplication {

  public static void main(String[] args) {
    SpringApplication.run(SandboxApplication.class, args);
  }
}
