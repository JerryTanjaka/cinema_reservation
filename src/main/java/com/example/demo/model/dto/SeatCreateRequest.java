package com.example.demo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SeatCreateRequest(@NotBlank String number, @NotNull UUID roomId) {}