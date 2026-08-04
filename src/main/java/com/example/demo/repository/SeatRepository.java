package com.example.demo.repository;

import com.example.demo.model.Seat;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, UUID> {}
