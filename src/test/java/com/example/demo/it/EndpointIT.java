package com.example.demo.it;

import com.example.demo.conf.FacadeIT;
import com.example.demo.endpoint.rest.security.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class EndpointIT extends FacadeIT {
  protected static final UUID CLIENT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
  protected static final UUID OTHER_CLIENT_ID =
      UUID.fromString("22222222-2222-2222-2222-222222222222");
  protected static final UUID EMPLOYEE_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
  protected static final UUID MANAGER_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
  protected static final UUID MOVIE_ID = UUID.fromString("20000000-0000-0000-0000-000000000001");
  protected static final UUID PROJECTION_ID =
      UUID.fromString("30000000-0000-0000-0000-000000000001");
  protected static final UUID RESERVATION_ID =
      UUID.fromString("40000000-0000-0000-0000-000000000001");

  @Autowired protected TestRestTemplate restTemplate;
  @Autowired protected JwtTokenService jwtTokenService;
  @Autowired protected ObjectMapper objectMapper;

  protected String token(String role, UUID userId) {
    return jwtTokenService.generateToken(userId.toString(), List.of(role));
  }

  protected HttpStatus status(HttpMethod method, String path, String bearer, String body) {
    return (HttpStatus) exchange(method, path, bearer, body).getStatusCode();
  }

  protected ResponseEntity<String> exchange(
      HttpMethod method, String path, String bearer, String body) {
    HttpHeaders headers = new HttpHeaders();
    if (bearer != null) {
      headers.setBearerAuth(bearer);
    }
    if (body != null) {
      headers.setContentType(MediaType.APPLICATION_JSON);
    }
    return restTemplate.exchange(path, method, new HttpEntity<>(body, headers), String.class);
  }
}
