package com.example.demo.endpoint.rest;

import com.example.demo.model.dto.RoomCreateRequest;
import com.example.demo.model.dto.RoomResponse;
import com.example.demo.service.RoomService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @PostMapping
  public ResponseEntity<RoomResponse> create(@Valid @RequestBody RoomCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<RoomResponse>> getAll() {
    return ResponseEntity.ok(roomService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<RoomResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(roomService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<RoomResponse> update(
      @PathVariable UUID id, @Valid @RequestBody RoomCreateRequest request) {
    return ResponseEntity.ok(roomService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    roomService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
