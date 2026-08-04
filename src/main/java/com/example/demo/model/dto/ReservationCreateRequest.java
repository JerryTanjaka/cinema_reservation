package com.example.demo.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ReservationCreateRequest(
    @NotNull UUID projectionId, @NotNull UUID userId, @NotEmpty List<UUID> seatIds) {}
