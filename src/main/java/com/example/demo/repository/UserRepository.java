package com.example.demo.repository;

import com.example.demo.repository.model.JUser;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<JUser, UUID> {}
