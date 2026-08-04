package com.example.demo.model;

import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record Reservation(
        UUID id,
        Instant createdAt,
        UUID projectionId,
        UUID userId,
        List<UUID> seatIds) {}