package com.example.demo.model.dto;

import java.util.UUID;

public record RoomResponse(UUID id, String number, int capacity) {}