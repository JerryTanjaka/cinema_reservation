package com.example.demo.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ReservationEndpointIT extends EndpointIT {
  private static final String RESERVATION_BODY =
      "{\"projectionId\":\"" + PROJECTION_ID + "\","
          + "\"userId\":\"" + CLIENT_ID + "\","
          + "\"seatIds\":[\"50000000-0000-0000-0000-000000000001\"]}";

  @Test
  void get_reservations_requires_authentication() {
    assertEquals(HttpStatus.UNAUTHORIZED, status(HttpMethod.GET, "/reservations", null, null));
  }

  @Test
  void get_reservations_is_forbidden_for_client() {
    assertEquals(
        HttpStatus.FORBIDDEN,
        status(HttpMethod.GET, "/reservations", token("CLIENT", CLIENT_ID), null));
  }

  @Test
  void get_reservations_is_allowed_for_employee_and_manager() throws Exception {
    ResponseEntity<String> employeeResponse =
        exchange(HttpMethod.GET, "/reservations", token("EMPLOYEE", EMPLOYEE_ID), null);
    assertEquals(HttpStatus.OK, employeeResponse.getStatusCode());
    assertContainsReservation(employeeResponse.getBody());
    ResponseEntity<String> managerResponse =
        exchange(HttpMethod.GET, "/reservations", token("MANAGER", MANAGER_ID), null);
    assertEquals(HttpStatus.OK, managerResponse.getStatusCode());
    assertContainsReservation(managerResponse.getBody());
  }

  @Test
  void get_reservation_by_id_requires_authentication() {
    assertEquals(
        HttpStatus.UNAUTHORIZED,
        status(HttpMethod.GET, "/reservations/" + RESERVATION_ID, null, null));
  }

  @Test
  void get_reservation_by_id_is_allowed_for_owner_client() {
    ResponseEntity<String> response =
        exchange(
            HttpMethod.GET,
            "/reservations/" + RESERVATION_ID,
            token("CLIENT", CLIENT_ID),
            null);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void get_reservation_by_id_is_forbidden_for_other_client() {
    assertEquals(
        HttpStatus.FORBIDDEN,
        status(
            HttpMethod.GET,
            "/reservations/" + RESERVATION_ID,
            token("CLIENT", OTHER_CLIENT_ID),
            null));
  }

  @Test
  void get_reservation_by_id_is_allowed_for_employee_and_manager() {
    assertEquals(
        HttpStatus.OK,
        status(
            HttpMethod.GET,
            "/reservations/" + RESERVATION_ID,
            token("EMPLOYEE", EMPLOYEE_ID),
            null));
    assertEquals(
        HttpStatus.OK,
        status(
            HttpMethod.GET,
            "/reservations/" + RESERVATION_ID,
            token("MANAGER", MANAGER_ID),
            null));
  }

  @Test
  void get_reservation_by_id_returns_404_for_unknown_id() {
    assertEquals(
        HttpStatus.NOT_FOUND,
        status(
            HttpMethod.GET,
            "/reservations/" + UUID.randomUUID(),
            token("MANAGER", MANAGER_ID),
            null));
  }

  @Test
  void put_reservation_requires_authentication() {
    assertEquals(
        HttpStatus.UNAUTHORIZED,
        status(HttpMethod.PUT, "/reservations/" + RESERVATION_ID, null, RESERVATION_BODY));
  }

  @Test
  void put_reservation_is_forbidden_for_client() {
    assertEquals(
        HttpStatus.FORBIDDEN,
        status(
            HttpMethod.PUT,
            "/reservations/" + RESERVATION_ID,
            token("CLIENT", CLIENT_ID),
            RESERVATION_BODY));
  }

  @Test
  void put_reservation_is_allowed_for_employee_and_manager() {
    assertEquals(
        HttpStatus.OK,
        status(
            HttpMethod.PUT,
            "/reservations/" + RESERVATION_ID,
            token("EMPLOYEE", EMPLOYEE_ID),
            RESERVATION_BODY));
    assertEquals(
        HttpStatus.OK,
        status(
            HttpMethod.PUT,
            "/reservations/" + RESERVATION_ID,
            token("MANAGER", MANAGER_ID),
            RESERVATION_BODY));
  }

  private void assertContainsReservation(String body) throws Exception {
    JsonNode array = objectMapper.readTree(body);
    assertTrue(array.isArray());
    boolean found =
        StreamSupport.stream(array.spliterator(), false)
            .anyMatch(node -> RESERVATION_ID.toString().equals(node.path("id").asText()));
    assertTrue(found);
  }
}
