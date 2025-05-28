package Caprish.Model.imp.mail;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sent_email")
public class SentEmail extends MyObject {

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

    public SentEmail(String receiver, String sender, String subject, String content,LocalDateTime sentAt) {
        this.receiver = receiver;
        this.sender = sender;
        this.subject = subject;
        this.content = content;
        this.success = false;
        this.errorMessage = "";
    }


}
