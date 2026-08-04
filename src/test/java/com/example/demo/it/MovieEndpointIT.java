package com.example.demo.it;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class MovieEndpointIT extends EndpointIT {
  private static final String MOVIE_BODY =
      "{\"title\":\"Dune\",\"genres\":[\"SCI_FI\"],\"duration\":\"PT2H28M\"}";

  @Test
  void put_movies_requires_authentication() {
    assertEquals(HttpStatus.UNAUTHORIZED, status(HttpMethod.PUT, "/movies", null, MOVIE_BODY));
  }

  @Test
  void put_movies_is_forbidden_for_client_and_employee() {
    assertEquals(
        HttpStatus.FORBIDDEN,
        status(HttpMethod.PUT, "/movies", token("CLIENT", CLIENT_ID), MOVIE_BODY));
    assertEquals(
        HttpStatus.FORBIDDEN,
        status(HttpMethod.PUT, "/movies", token("EMPLOYEE", EMPLOYEE_ID), MOVIE_BODY));
  }

  @Test
  void put_movies_is_allowed_for_manager() {
    assertEquals(
        HttpStatus.OK, status(HttpMethod.PUT, "/movies", token("MANAGER", MANAGER_ID), MOVIE_BODY));
  }
}
