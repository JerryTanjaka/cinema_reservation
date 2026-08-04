package com.example.demo.model.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record RoomResponse(UUID id, String number, int capacity) {}
