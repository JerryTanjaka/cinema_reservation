package com.example.demo.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record ProjectionCreateRequest(
        @NotNull Instant datetime,
        @NotNull BigDecimal seatPrice,
        @NotNull UUID roomId,
        @NotNull UUID movieId) {}