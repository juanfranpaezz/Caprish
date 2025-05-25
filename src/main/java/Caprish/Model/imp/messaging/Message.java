package Caprish.Model.imp.messaging;

import Caprish.Model.imp.MyObject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="message")
public class Message extends MyObject {
    @ManyToOne(optional = false)
    @JsonBackReference("message-chat")
    @JoinColumn(name = "id_chat", nullable = false)
    private Chat chat;

    @Column(name = "id_sender", nullable = false)
    private Long senderId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime sent_at = LocalDateTime.now();}
