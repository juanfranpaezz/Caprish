package Caprish.Model.imp.mail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email_token")//NO CAMBIAR, TABLA DE BDD QUE QUEDE ASI PORFA, TABLA QUE GUARDA LOS TOKENS!!
public class EmailToken {

    private String email;
    private String token;
    private LocalDateTime expiration;
    private boolean verified;

}
