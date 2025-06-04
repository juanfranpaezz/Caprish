package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObject;
import Caprish.Model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "credential")
public class Credential extends MyObject {

    @Column(columnDefinition = "text", nullable = false)
    private String first_name;

    @Column(columnDefinition = "text", nullable = false)
    private String last_name;

    @Column(columnDefinition = "text", nullable = false, unique = true)
    @Email
    private String username;      // aquí guardás el email

    @Column(columnDefinition = "text", nullable = false)
    private String password; // aquí guardás el BCrypt hash

    @Column(nullable = false)
    private boolean enabled = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role")
    private Role role;

    public String getCompleteName() {
        return first_name + " " + last_name;
    }

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credential credential)) return false;
        return Objects.equals(username, credential.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

