package com.example.demo.model;

import com.example.demo.model.enums.Genre;

import java.time.Duration;
import java.util.UUID;

public record Movie(UUID id, String title, Genre genre, String description, Duration duration) {}