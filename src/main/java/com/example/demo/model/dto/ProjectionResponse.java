package com.example.demo.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record ProjectionResponse(
        UUID id,
        Instant datetime,
        BigDecimal seatPrice,
        UUID roomId,
        UUID movieId) {}