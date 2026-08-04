package com.example.demo.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record Projection(
        UUID id,
        Instant datetime,
        BigDecimal seatPrice,
        UUID roomId,
        UUID movieId) {}