package com.example.demo.model.dto;

import com.example.demo.model.enums.Genre;

import java.time.Duration;
import java.util.UUID;

public record MovieResponse(UUID id, String title, Genre genre, String description, Duration duration) {}