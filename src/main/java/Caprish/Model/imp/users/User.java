package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObjects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public abstract class User extends MyObjects {

    @Column(unique=true, columnDefinition = "text")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    private String email;

    @Column(name = "password_hash", columnDefinition = "text")
    @NotBlank(message = "La contraseña no puede estar vacia.")
    private String passwordHash;


}
