package com.example.demo.model.dto;

import com.example.demo.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserCreateRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull LocalDate birthdate,
        @NotBlank @Email String email,
        @NotBlank String password,
        String phone,
        @NotNull UserRole role) {}