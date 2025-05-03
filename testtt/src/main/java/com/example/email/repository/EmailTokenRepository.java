package com.example.email.repository;

import com.example.email.model.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
    Optional<EmailToken> findByEmail(String email);
    Optional<EmailToken> findByEmailAndToken(String email, String token);
}
