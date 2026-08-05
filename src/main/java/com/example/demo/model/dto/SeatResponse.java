package com.example.demo.model.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record SeatResponse(UUID id, String number, UUID roomId) {}
