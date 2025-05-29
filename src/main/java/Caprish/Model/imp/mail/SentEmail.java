package Caprish.Model.imp.mail;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sent_email")
public class SentEmail extends MyObject {

    @Email
    @NotBlank
    @Column(nullable = false)
    private String receiver;
    @Column(nullable = false)
    @Email
    @NotBlank
    private String sender;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private LocalDateTime sentAt;


    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean success;

    @Column(columnDefinition = "TEXT")
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
