package com.example.demo.endpoint.rest.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
  private final JwtConf jwtConf;
  private final SecretKey key;

  public JwtTokenService(JwtConf jwtConf) {
    this.jwtConf = jwtConf;
    this.key = Keys.hmacShaKeyFor(jwtConf.secret().getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String subject, List<String> roles) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(subject)
        .claim("roles", roles)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusMillis(jwtConf.expirationMs())))
        .signWith(key)
        .compact();
  }

  public Claims parseToken(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }
}
