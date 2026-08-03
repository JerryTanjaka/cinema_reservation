package com.example.demo.repository;

import com.example.demo.repository.model.JProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectionRepository extends JpaRepository<JProjection, UUID> {}
