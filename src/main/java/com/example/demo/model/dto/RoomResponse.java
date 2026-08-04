package com.example.demo.model.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RoomResponse(UUID id, String number, int capacity) {}