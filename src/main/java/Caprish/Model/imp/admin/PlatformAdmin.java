package Caprish.Model.imp.admin;
import Caprish.Model.imp.MyObjects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="platform_admin")
public class PlatformAdmin extends MyObjects {

    @Column(unique=true, columnDefinition = "text",nullable=false)
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    private String email;

    @Column(columnDefinition = "TEXT",nullable=false)
    @NotBlank(message="La contraseña no puede estar vacia")
    private String password_hash;

    @Column(nullable=false)
    private LocalDate created_at;

    public PlatformAdmin(String password_hash, String email, LocalDate created_at) {
        this.password_hash = password_hash;
        this.email = email;
        this.created_at = created_at;
    }
    //    String sql =    "CREATE TABLE platform_admin (\n" +
//                    "  id_platform_admin          BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
//                    "  email                  VARCHAR(150) NOT NULL UNIQUE,\n" +
//                    "  password_hash          VARCHAR(255) NOT NULL,\n" +
//                    "  created_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
//                    ");\n";
}
