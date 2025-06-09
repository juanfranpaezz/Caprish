package Caprish.Model.imp.mail;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "email_token")
public class EmailToken extends MyObject {

    @NotBlank
    @Email
    @Column(nullable = false, length = 255)
    private String email;

    @NotBlank
    @Column(nullable = false,columnDefinition = "TEXT")
    String password;

    @NotBlank
    @Column(nullable = false, length = 6)
    private String token;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private LocalDateTime expiration;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean verified;
}
