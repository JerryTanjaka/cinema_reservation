package com.example.demo.repository;

import com.example.demo.repository.model.JReservation;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<JReservation, UUID> {}
