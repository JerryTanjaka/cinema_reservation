package com.example.demo.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Room(UUID id, String number, int capacity) {}