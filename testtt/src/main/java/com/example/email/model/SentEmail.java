package com.example.email.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;
// entidad que basicamente es un log de los mails mandados (menos los mails que mandan el token)
@Entity
@Table(name = "sent_email")
public class SentEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String receiver;
    @Column(nullable = false)
    private String sender;
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime sentAt;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean success;

    private String errorMessage;

    public SentEmail() {}

    public SentEmail(String receiver, String sender, String subject, String content, LocalDateTime sentAt) {
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.sender = sender;
        this.sentAt = sentAt;
        this.success = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
