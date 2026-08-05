package com.example.demo.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.conf.FacadeIT;
import com.example.demo.endpoint.rest.security.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;

class SecurityIT extends FacadeIT {
  private static final UUID CLIENT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
  private static final UUID OTHER_CLIENT_ID =
      UUID.fromString("22222222-2222-2222-2222-222222222222");
  private static final UUID EMPLOYEE_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
  private static final UUID MANAGER_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
  private static final UUID MOVIE_ID = UUID.fromString("20000000-0000-0000-0000-000000000001");
  private static final UUID PROJECTION_ID = UUID.fromString("30000000-0000-0000-0000-000000000001");
  private static final UUID RESERVATION_ID =
      UUID.fromString("40000000-0000-0000-0000-000000000001");
  private static final String MOVIE_BODY =
      "{\"title\":\"Dune\",\"genres\":[\"SCI_FI\"],\"duration\":\"PT2H28M\"}";
  private static final String PROJECTION_BODY =
      "{\"datetime\":\"2026-08-10T20:00:00Z\",\"seatPrice\":12.50,"
          + "\"roomId\":\"10000000-0000-0000-0000-000000000001\","
          + "\"movieId\":\"20000000-0000-0000-0000-000000000001\"}";
  private static final String RESERVATION_BODY =
      "{\"projectionId\":\"30000000-0000-0000-0000-000000000001\","
          + "\"userId\":\"11111111-1111-1111-1111-111111111111\","
          + "\"seatIds\":[\"50000000-0000-0000-0000-000000000001\"]}";

  @Autowired TestRestTemplate restTemplate;
  @Autowired JwtTokenService jwtTokenService;
  @Autowired ObjectMapper objectMapper;

  @BeforeEach
  void configureHttpClient() {
    restTemplate
        .getRestTemplate()
        .setRequestFactory(new JdkClientHttpRequestFactory(HttpClient.newBuilder().build()));
  }

  @Test
  void getMovies_is_public() {
    assertEquals(HttpStatus.OK, get("/movies", null));
    assertEquals(HttpStatus.OK, get("/movies/" + MOVIE_ID, null));
    assertEquals(HttpStatus.OK, get("/movies", token("CLIENT")));
    assertEquals(HttpStatus.OK, get("/movies/" + MOVIE_ID, token("CLIENT")));
  }

  @Test
  void movies_write_access_rules() {
    assertEquals(HttpStatus.UNAUTHORIZED, post("/movies", null, MOVIE_BODY));
    assertEquals(HttpStatus.FORBIDDEN, post("/movies", token("CLIENT"), MOVIE_BODY));
    assertEquals(HttpStatus.FORBIDDEN, post("/movies", token("EMPLOYEE"), MOVIE_BODY));
    assertEquals(HttpStatus.CREATED, post("/movies", token("MANAGER"), MOVIE_BODY));

    assertEquals(HttpStatus.UNAUTHORIZED, put("/movies/" + MOVIE_ID, null, MOVIE_BODY));
    assertEquals(HttpStatus.FORBIDDEN, put("/movies/" + MOVIE_ID, token("CLIENT"), MOVIE_BODY));
    assertEquals(HttpStatus.FORBIDDEN, put("/movies/" + MOVIE_ID, token("EMPLOYEE"), MOVIE_BODY));
    assertEquals(HttpStatus.OK, put("/movies/" + MOVIE_ID, token("MANAGER"), MOVIE_BODY));

    assertEquals(HttpStatus.UNAUTHORIZED, delete("/movies/" + MOVIE_ID, null));
    assertEquals(HttpStatus.FORBIDDEN, delete("/movies/" + MOVIE_ID, token("CLIENT")));
    assertEquals(HttpStatus.FORBIDDEN, delete("/movies/" + MOVIE_ID, token("EMPLOYEE")));
  }

  @Test
  void manager_can_delete_movie() throws Exception {
    ResponseEntity<String> created = postWithBody("/movies", token("MANAGER"), MOVIE_BODY);
    assertEquals(HttpStatus.CREATED, created.getStatusCode());
    String id = objectMapper.readTree(created.getBody()).path("id").asText();
    assertEquals(HttpStatus.NO_CONTENT, delete("/movies/" + id, token("MANAGER")));
  }

  @Test
  void getProjections_is_public() {
    assertEquals(HttpStatus.OK, get("/projections", null));
    assertEquals(HttpStatus.OK, get("/projections/" + PROJECTION_ID, null));
    assertEquals(HttpStatus.OK, get("/projections", token("CLIENT")));
    assertEquals(HttpStatus.OK, get("/projections/" + PROJECTION_ID, token("CLIENT")));
  }

  @Test
  void projections_write_access_rules() {
    assertEquals(HttpStatus.UNAUTHORIZED, post("/projections", null, PROJECTION_BODY));
    assertEquals(HttpStatus.FORBIDDEN, post("/projections", token("CLIENT"), PROJECTION_BODY));
    assertEquals(HttpStatus.FORBIDDEN, post("/projections", token("EMPLOYEE"), PROJECTION_BODY));
    assertEquals(HttpStatus.CREATED, post("/projections", token("MANAGER"), PROJECTION_BODY));

    assertEquals(
        HttpStatus.UNAUTHORIZED, put("/projections/" + PROJECTION_ID, null, PROJECTION_BODY));
    assertEquals(
        HttpStatus.FORBIDDEN,
        put("/projections/" + PROJECTION_ID, token("CLIENT"), PROJECTION_BODY));
    assertEquals(
        HttpStatus.FORBIDDEN,
        put("/projections/" + PROJECTION_ID, token("EMPLOYEE"), PROJECTION_BODY));
    assertEquals(
        HttpStatus.OK, put("/projections/" + PROJECTION_ID, token("MANAGER"), PROJECTION_BODY));

    assertEquals(HttpStatus.UNAUTHORIZED, delete("/projections/" + PROJECTION_ID, null));
    assertEquals(HttpStatus.FORBIDDEN, delete("/projections/" + PROJECTION_ID, token("CLIENT")));
    assertEquals(HttpStatus.FORBIDDEN, delete("/projections/" + PROJECTION_ID, token("EMPLOYEE")));
  }

  @Test
  void manager_can_delete_projection() throws Exception {
    ResponseEntity<String> created =
        postWithBody("/projections", token("MANAGER"), PROJECTION_BODY);
    assertEquals(HttpStatus.CREATED, created.getStatusCode());
    String id = objectMapper.readTree(created.getBody()).path("id").asText();
    assertEquals(HttpStatus.NO_CONTENT, delete("/projections/" + id, token("MANAGER")));
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
    String path = "/reservations/" + RESERVATION_ID;
    assertEquals(HttpStatus.UNAUTHORIZED, get(path, null));
    assertEquals(HttpStatus.OK, get(path, token("CLIENT", CLIENT_ID)));
    assertEquals(HttpStatus.FORBIDDEN, get(path, token("CLIENT", OTHER_CLIENT_ID)));
    assertEquals(HttpStatus.OK, get(path, token("EMPLOYEE", EMPLOYEE_ID)));
    assertEquals(HttpStatus.OK, get(path, token("MANAGER", MANAGER_ID)));
  }

  @Test
  void reservations_write_access_rules() {
    assertEquals(HttpStatus.UNAUTHORIZED, post("/reservations", null, RESERVATION_BODY));
    assertEquals(HttpStatus.FORBIDDEN, post("/reservations", token("CLIENT"), RESERVATION_BODY));
    assertEquals(HttpStatus.CREATED, post("/reservations", token("EMPLOYEE"), RESERVATION_BODY));
    assertEquals(HttpStatus.CREATED, post("/reservations", token("MANAGER"), RESERVATION_BODY));

    assertEquals(
        HttpStatus.UNAUTHORIZED, put("/reservations/" + RESERVATION_ID, null, RESERVATION_BODY));
    assertEquals(
        HttpStatus.FORBIDDEN,
        put("/reservations/" + RESERVATION_ID, token("CLIENT"), RESERVATION_BODY));
    assertEquals(
        HttpStatus.OK, put("/reservations/" + RESERVATION_ID, token("EMPLOYEE"), RESERVATION_BODY));
    assertEquals(
        HttpStatus.OK, put("/reservations/" + RESERVATION_ID, token("MANAGER"), RESERVATION_BODY));

    assertEquals(HttpStatus.UNAUTHORIZED, delete("/reservations/" + RESERVATION_ID, null));
    assertEquals(HttpStatus.FORBIDDEN, delete("/reservations/" + RESERVATION_ID, token("CLIENT")));
  }

  @Test
  void employee_can_delete_reservation() throws Exception {
    ResponseEntity<String> created =
        postWithBody("/reservations", token("EMPLOYEE"), RESERVATION_BODY);
    assertEquals(HttpStatus.CREATED, created.getStatusCode());
    String id = objectMapper.readTree(created.getBody()).path("id").asText();
    assertEquals(HttpStatus.NO_CONTENT, delete("/reservations/" + id, token("EMPLOYEE")));
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

  private String token(String role, UUID userId) {
    return jwtTokenService.generateToken(userId.toString(), List.of(role));
  }

  private HttpStatus get(String path, String token) {
    return status(HttpMethod.GET, path, token, null);
  }

  private HttpStatus post(String path, String token, String body) {
    return status(HttpMethod.POST, path, token, body);
  }

  private HttpStatus put(String path, String token, String body) {
    return status(HttpMethod.PUT, path, token, body);
  }

  private HttpStatus delete(String path, String token) {
    return status(HttpMethod.DELETE, path, token, null);
  }

  private ResponseEntity<String> postWithBody(String path, String token, String body) {
    return exchange(HttpMethod.POST, path, token, body);
  }

  private HttpStatus status(HttpMethod method, String path, String bearer, String body) {
    return (HttpStatus) exchange(method, path, bearer, body).getStatusCode();
  }

  private ResponseEntity<String> exchange(
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
