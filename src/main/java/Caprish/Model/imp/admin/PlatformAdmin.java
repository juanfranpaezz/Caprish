package Caprish.Model.imp.admin;
import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="platform_admin")
public class PlatformAdmin extends MyObject {

    @Column(unique=true, columnDefinition = "text",nullable=false)
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    private String email;

    @Column(columnDefinition = "TEXT",nullable=false)
    @NotBlank(message="La contraseña no puede estar vacia")
    private String password_hash;

    @Column(nullable=false)
    private LocalDate created_at;

}
