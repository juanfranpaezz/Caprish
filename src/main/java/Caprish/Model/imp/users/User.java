package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObjects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class User extends MyObjects {

    @Column(unique=true, columnDefinition = "text")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    private String email;

    @Column(columnDefinition = "text")
    @NotBlank(message = "La contraseña no puede estar vacia.")
    private String password_hash;

    public User(String email, String password_hash) {
        this.email = email;
        this.password_hash = password_hash;
    }

    /*padre de: client y staff, */
}
