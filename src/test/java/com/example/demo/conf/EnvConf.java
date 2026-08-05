package com.example.demo.conf;

import org.springframework.test.context.DynamicPropertyRegistry;

public class EnvConf {
  public void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("jwt.secret", () -> "test-secret-test-secret-test-secret-test-secret");
    registry.add("jwt.expiration", () -> "86400000");
  }
}
