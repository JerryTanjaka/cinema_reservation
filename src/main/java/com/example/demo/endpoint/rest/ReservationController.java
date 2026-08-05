package com.example.demo.endpoint.rest;

import com.example.demo.model.dto.ReservationCreateRequest;
import com.example.demo.model.dto.ReservationResponse;
import com.example.demo.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping
  public ResponseEntity<ReservationResponse> create(
      @Valid @RequestBody ReservationCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<ReservationResponse>> getAll() {
    return ResponseEntity.ok(reservationService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(reservationService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReservationResponse> update(
      @PathVariable UUID id, @Valid @RequestBody ReservationCreateRequest request) {
    return ResponseEntity.ok(reservationService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    reservationService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
