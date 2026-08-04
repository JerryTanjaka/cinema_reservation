package com.example.demo.endpoint.rest;

import com.example.demo.model.dto.MovieCreateRequest;
import com.example.demo.model.dto.MovieResponse;
import com.example.demo.service.MovieService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @PostMapping
  public ResponseEntity<MovieResponse> create(@Valid @RequestBody MovieCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(movieService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<MovieResponse>> getAll() {
    return ResponseEntity.ok(movieService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<MovieResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(movieService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<MovieResponse> update(
      @PathVariable UUID id, @Valid @RequestBody MovieCreateRequest request) {
    return ResponseEntity.ok(movieService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    movieService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
