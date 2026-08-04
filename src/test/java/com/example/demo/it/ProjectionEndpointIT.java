package com.example.demo.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ProjectionEndpointIT extends EndpointIT {
  private static final String PROJECTION_BODY =
      "{\"datetime\":\"2026-08-10T20:00:00Z\",\"seatPrice\":12.50,"
          + "\"room\":{\"id\":\"10000000-0000-0000-0000-000000000001\"},"
          + "\"movie\":{\"id\":\""
          + MOVIE_ID
          + "\"}}";

  @Test
  void put_projection_requires_authentication() {
    assertEquals(
        HttpStatus.UNAUTHORIZED, status(HttpMethod.PUT, "/projection", null, PROJECTION_BODY));
  }

  @Test
  void put_projection_is_forbidden_for_client_and_employee() {
    assertEquals(
        HttpStatus.FORBIDDEN,
        status(HttpMethod.PUT, "/projection", token("CLIENT", CLIENT_ID), PROJECTION_BODY));
    assertEquals(
        HttpStatus.FORBIDDEN,
        status(HttpMethod.PUT, "/projection", token("EMPLOYEE", EMPLOYEE_ID), PROJECTION_BODY));
  }

  @Test
  void put_projection_is_allowed_for_manager() {
    assertEquals(
        HttpStatus.OK,
        status(HttpMethod.PUT, "/projection", token("MANAGER", MANAGER_ID), PROJECTION_BODY));
  }

  @Test
  void get_projections_is_public_and_returns_seeded_projection() throws Exception {
    ResponseEntity<String> response = exchange(HttpMethod.GET, "/projections", null, null);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    JsonNode array = objectMapper.readTree(response.getBody());
    assertTrue(array.isArray());
    boolean found =
        StreamSupport.stream(array.spliterator(), false)
            .anyMatch(node -> PROJECTION_ID.toString().equals(node.path("id").asText()));
    assertTrue(found);
  }
}
