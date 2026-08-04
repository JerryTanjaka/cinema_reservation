package com.example.demo.repository;

import com.example.demo.model.Movie;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, UUID> {}
