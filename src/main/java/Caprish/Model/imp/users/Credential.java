package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObject;
import Caprish.Model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "credential")
public class Credential extends MyObject {

    @Column(columnDefinition = "text")
    private String first_name;

    @Column(columnDefinition = "text")
    private String last_name;

    @Column(columnDefinition = "text", nullable = false, unique = true)
    @Email
    private String username;

    @Column(columnDefinition = "text", nullable = false)
    private String password;

    @Column
    private boolean enabled = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role")
    private Role role;

    @Column(nullable = false)
    private Long tokenVersion = 0L;
    public Credential (String email, String password,Role role){
        this.password=password;
        this.username=email;
        this.role=role;
        this.tokenVersion= 2L;
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