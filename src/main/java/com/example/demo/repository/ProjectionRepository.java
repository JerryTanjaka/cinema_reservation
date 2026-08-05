package com.example.demo.repository;

import com.example.demo.repository.model.JProjection;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectionRepository extends JpaRepository<JProjection, UUID> {}
