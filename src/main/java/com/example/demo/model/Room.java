package com.example.demo.model;

import java.util.UUID;

public record Room(UUID id, String number, int capacity) {}