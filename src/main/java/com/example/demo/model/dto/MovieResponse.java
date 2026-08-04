package com.example.demo.model.dto;

import com.example.demo.model.enums.Genre;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record MovieResponse(
    UUID id, String title, List<Genre> genres, String description, Duration duration) {}
