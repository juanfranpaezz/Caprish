package com.example.email.repository;

import com.example.email.model.SentEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentEmailRepository extends JpaRepository<SentEmail, Long> {
    /** NO TOCAR LOS NOMBRES DE LOS METODOS GRACIAS**/
    // busca por gente que recibió
    List<SentEmail> findAllByReceiverIgnoreCase(String receiver);
    // busca por gente que mandó mails
    List<SentEmail> findAllBySenderIgnoreCase(String sender);
}
