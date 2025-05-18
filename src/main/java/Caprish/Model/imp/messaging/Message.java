package Caprish.Model.imp.messaging;

import Caprish.Model.enums.SenderType;
import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message extends MyObject {
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_chat", nullable = false)
    private Chat chat;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_type", nullable = false)
    private SenderType senderType;

    @Column(name = "id_sender", nullable = false)
    private Long id_sender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime sent_at = LocalDateTime.now();

}

