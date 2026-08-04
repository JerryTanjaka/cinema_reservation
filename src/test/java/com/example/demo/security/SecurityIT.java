package com.example.demo.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.conf.FacadeIT;
import com.example.demo.endpoint.rest.security.JwtTokenService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class SecurityIT extends FacadeIT {
  @Autowired TestRestTemplate restTemplate;
  @Autowired JwtTokenService jwtTokenService;

  @Test
  void putMovies_access_rules() {
    assertEquals(HttpStatus.UNAUTHORIZED, put("/movies", null));
    assertEquals(HttpStatus.FORBIDDEN, put("/movies", token("CLIENT")));
    assertEquals(HttpStatus.FORBIDDEN, put("/movies", token("EMPLOYEE")));
    assertEquals(HttpStatus.OK, put("/movies", token("MANAGER")));
  }

  @Test
  void getReservations_access_rules() {
    assertEquals(HttpStatus.UNAUTHORIZED, get("/reservations", null));
    assertEquals(HttpStatus.FORBIDDEN, get("/reservations", token("CLIENT")));
    assertEquals(HttpStatus.OK, get("/reservations", token("EMPLOYEE")));
    assertEquals(HttpStatus.OK, get("/reservations", token("MANAGER")));
  }

  @Test
  void getReservationById_access_rules() {
    String path = "/reservationById/" + UUID.randomUUID();
    assertEquals(HttpStatus.UNAUTHORIZED, get(path, null));
    assertEquals(HttpStatus.OK, get(path, token("CLIENT")));
    assertEquals(HttpStatus.OK, get(path, token("EMPLOYEE")));
    assertEquals(HttpStatus.OK, get(path, token("MANAGER")));
  }

  @Test
  void putReservation_access_rules() {
    assertEquals(HttpStatus.UNAUTHORIZED, put("/reservation", null));
    assertEquals(HttpStatus.FORBIDDEN, put("/reservation", token("CLIENT")));
    assertEquals(HttpStatus.OK, put("/reservation", token("EMPLOYEE")));
    assertEquals(HttpStatus.OK, put("/reservation", token("MANAGER")));
  }

  @Test
  void putProjection_access_rules() {
    assertEquals(HttpStatus.UNAUTHORIZED, put("/projection", null));
    assertEquals(HttpStatus.FORBIDDEN, put("/projection", token("CLIENT")));
    assertEquals(HttpStatus.FORBIDDEN, put("/projection", token("EMPLOYEE")));
    assertEquals(HttpStatus.OK, put("/projection", token("MANAGER")));
  }

  @Test
  void getProjections_is_public() {
    assertEquals(HttpStatus.OK, get("/projections", null));
    assertEquals(HttpStatus.OK, get("/projections", token("CLIENT")));
    assertEquals(HttpStatus.OK, get("/projections", token("EMPLOYEE")));
    assertEquals(HttpStatus.OK, get("/projections", token("MANAGER")));
  }

  @Test
  void invalid_token_is_rejected() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth("invalid-token");
    HttpEntity<Void> entity = new HttpEntity<>(headers);
    ResponseEntity<Void> response =
        restTemplate.exchange("/reservations", HttpMethod.GET, entity, Void.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  private String token(String role) {
    return jwtTokenService.generateToken(UUID.randomUUID().toString(), List.of(role));
  }

  private HttpStatus get(String path, String token) {
    HttpHeaders headers = headers(token);
    ResponseEntity<Void> response =
        restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<>(headers), Void.class);
    return (HttpStatus) response.getStatusCode();
  }

  private HttpStatus put(String path, String token) {
    HttpHeaders headers = headers(token);
    ResponseEntity<Void> response =
        restTemplate.exchange(path, HttpMethod.PUT, new HttpEntity<>(headers), Void.class);
    return (HttpStatus) response.getStatusCode();
  }

  private HttpHeaders headers(String token) {
    HttpHeaders headers = new HttpHeaders();
    if (token != null) {
      headers.setBearerAuth(token);
    }
    return headers;
  }
}
