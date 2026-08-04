package com.example.demo.endpoint.rest;

import com.example.demo.model.dto.ProjectionCreateRequest;
import com.example.demo.model.dto.ProjectionResponse;
import com.example.demo.service.ProjectionService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projections")
@RequiredArgsConstructor
public class ProjectionController {

  private final ProjectionService projectionService;

  @PostMapping
  public ResponseEntity<ProjectionResponse> create(
      @Valid @RequestBody ProjectionCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(projectionService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<ProjectionResponse>> getAll() {
    return ResponseEntity.ok(projectionService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectionResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(projectionService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProjectionResponse> update(
      @PathVariable UUID id, @Valid @RequestBody ProjectionCreateRequest request) {
    return ResponseEntity.ok(projectionService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    projectionService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
