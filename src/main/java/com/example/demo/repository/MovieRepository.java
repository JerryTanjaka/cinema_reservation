package com.example.demo.repository;

import com.example.demo.repository.model.JMovie;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<JMovie, UUID> {}
