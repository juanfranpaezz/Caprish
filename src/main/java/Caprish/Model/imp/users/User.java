package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends MyObject {

    @Column(columnDefinition = "text", nullable = false)
    private String first_name;

    @Column(columnDefinition = "text", nullable = false)
    private String last_name;

    @Column(columnDefinition = "text", nullable = false, unique = true)
    @Email
    private String username;

    @Column(columnDefinition = "text", nullable = false)
    private String password_hash;

    @Column(nullable = false)
    private boolean enabled = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role")
    private Role role;



    public String getCompleteName() {
        return first_name + " " + last_name;
    }

    public User(String username, String password_hash) {
        this.username = username;
        this.password_hash = password_hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
