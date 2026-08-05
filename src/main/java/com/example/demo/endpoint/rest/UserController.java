package com.example.demo.endpoint.rest;

import com.example.demo.model.dto.UserCreateRequest;
import com.example.demo.model.dto.UserResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> getAll() {
    return ResponseEntity.ok(userService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(userService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(
      @PathVariable UUID id, @Valid @RequestBody UserCreateRequest request) {
    return ResponseEntity.ok(userService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
