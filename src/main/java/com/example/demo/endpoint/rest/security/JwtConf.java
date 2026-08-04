package com.example.demo.endpoint.rest.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConf {
  private final String secret;
  private final long expirationMs;

  public JwtConf(
      @Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expirationMs) {
    this.secret = secret;
    this.expirationMs = expirationMs;
  }

  public String secret() {
    return secret;
  }

  public long expirationMs() {
    return expirationMs;
  }
}
