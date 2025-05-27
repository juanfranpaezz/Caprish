package Caprish.Model.imp.messaging;

import Caprish.Model.enums.BranchType;
import Caprish.Model.enums.SenderType;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sender_type", nullable = false)
    private SenderType sender_type;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime sent_at = LocalDateTime.now();}
