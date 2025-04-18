package com.griddynamics.mamaievm.samplestoreapp.repository;

import com.griddynamics.mamaievm.samplestoreapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.validation.constraints.Email;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@Email(message = "Incorrect email") String email);
    
    boolean existsByEmail(String email);
}