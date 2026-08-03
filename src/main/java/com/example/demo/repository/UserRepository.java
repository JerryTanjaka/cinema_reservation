package com.example.demo.repository;

import com.example.demo.repository.model.JUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<JUser, UUID> {}