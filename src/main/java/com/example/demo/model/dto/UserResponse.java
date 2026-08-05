package com.example.demo.model.dto;

import com.example.demo.model.enums.UserRole;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserResponse(
    UUID id,
    String firstName,
    String lastName,
    LocalDate birthdate,
    String email,
    String phone,
    UserRole role) {}
