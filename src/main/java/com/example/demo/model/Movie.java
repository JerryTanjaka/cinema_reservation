package com.example.demo.model;

import com.example.demo.model.enums.Genre;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public record Movie(UUID id, String title, List<Genre> genres, String description, Duration duration) {}