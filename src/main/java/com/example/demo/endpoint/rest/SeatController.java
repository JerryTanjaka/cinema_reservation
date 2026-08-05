package com.example.demo.endpoint.rest;

import com.example.demo.model.dto.SeatCreateRequest;
import com.example.demo.model.dto.SeatResponse;
import com.example.demo.service.SeatService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatController {

  private final SeatService seatService;

  @PostMapping
  public ResponseEntity<SeatResponse> create(@Valid @RequestBody SeatCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(seatService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<SeatResponse>> getAll() {
    return ResponseEntity.ok(seatService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SeatResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(seatService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<SeatResponse> update(
      @PathVariable UUID id, @Valid @RequestBody SeatCreateRequest request) {
    return ResponseEntity.ok(seatService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    seatService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
