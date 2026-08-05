package com.example.demo.repository;

import com.example.demo.repository.model.JSeat;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<JSeat, UUID> {}
