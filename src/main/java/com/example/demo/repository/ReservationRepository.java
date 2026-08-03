package com.example.demo.repository;

import com.example.demo.repository.model.JReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<JReservation, UUID> {}
