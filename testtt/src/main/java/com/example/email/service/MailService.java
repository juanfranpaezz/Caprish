package com.example.email.service;

import com.example.email.model.Mail;
import com.example.email.repository.MailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {
    private final MailRepository mailRepository;
    public MailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }
    //agrega un mail a la bdd
    public void addMail(String texto,String password) {
        Mail mail = new Mail(texto,password);
        mailRepository.save(mail);
    }
    //busca si el email esta en la bdd
    public boolean existsByMail(String email) {
        return mailRepository.existsByMail(email);
    }

    //verificador de login
    public void verifyLogin(String mail, String password) {
        if (!mailRepository.findByMailAndPassword(mail, password).isPresent()) {
            throw new RuntimeException("Credenciales inválidas");
        }
    }
}
