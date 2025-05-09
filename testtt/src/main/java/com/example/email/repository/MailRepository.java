package com.example.email.repository;

import com.example.email.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
    boolean existsByMail(String email);
    boolean findByPassword(String password);
    Optional<Mail> findByMailAndPassword(String mail, String password);
}
