package com.example.demo.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Seat(UUID id, String number, UUID roomId) {}