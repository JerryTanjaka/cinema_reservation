package com.example.demo.repository;

import com.example.demo.repository.model.JSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<JSeat, UUID> {

}
