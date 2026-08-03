package com.example.demo.model.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ReservationResponse(
        UUID id,
        Instant createdAt,
        UUID projectionId,
        UUID userId,
        List<UUID> seatIds) {}