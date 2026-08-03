package com.example.demo.model.dto;

import com.example.demo.model.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record MovieCreateRequest(
        @NotBlank String title,
        @NotNull Genre genre,
        String description,
        @NotNull Duration duration) {}