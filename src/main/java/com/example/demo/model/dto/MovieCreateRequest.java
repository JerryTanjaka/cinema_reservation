package com.example.demo.model.dto;

import com.example.demo.model.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Duration;
import java.util.List;

@Builder
public record MovieCreateRequest(
        @NotBlank String title,
        @NotEmpty List<Genre> genres,
        String description,
        @NotNull Duration duration) {}