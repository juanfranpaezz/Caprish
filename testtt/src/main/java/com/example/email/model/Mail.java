package com.example.email.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mail")// modificar dependiendo como queda la estructura de bdd, esto podria ser la clase usuario tranquilamente, teniendo toda la info de los mismos
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String password;
    public Mail() {}

    public Mail(String mail,String password) {
        this.mail=mail;
        this.password=password;
    }

    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
