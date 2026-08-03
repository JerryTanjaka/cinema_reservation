package com.example.demo.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RoomCreateRequest(@NotBlank String number, @Min(1) int capacity) {}