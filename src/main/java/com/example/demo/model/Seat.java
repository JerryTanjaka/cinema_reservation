package com.example.demo.model;

import java.util.UUID;

public record Seat(UUID id, String number, UUID roomId) {}