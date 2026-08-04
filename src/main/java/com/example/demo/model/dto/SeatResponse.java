package com.example.demo.model.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SeatResponse(UUID id, String number, UUID roomId) {}