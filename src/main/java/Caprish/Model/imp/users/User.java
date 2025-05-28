package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public abstract class User extends MyObject {

    @Column(unique=true, columnDefinition = "text")
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    private String email;

    @Column(name = "password_hash", columnDefinition = "text")
    @NotBlank(message = "La contraseña no puede estar vacia.")
    private String password_hash;

    @Column(columnDefinition = "text",nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String first_name;

    @Column(columnDefinition = "text",nullable = false)
    @NotBlank(message = "El apellido no puede estar vacío")
    private String last_name;

    public String getCompleteName(){
        return first_name + " " + last_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(email, user.email) && Objects.equals(password_hash, user.password_hash);
    }

    public User(String email, String password_hash) {
        this.email = email;
        this.password_hash = password_hash;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password_hash);
    }
}
