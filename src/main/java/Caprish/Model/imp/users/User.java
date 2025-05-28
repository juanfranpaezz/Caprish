package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class User extends MyObject {

    @Column(columnDefinition = "text", nullable = false)
    private String first_name;

    @Column(columnDefinition = "text", nullable = false)
    private String last_name;

    @Column(columnDefinition = "text", nullable = false, unique = true)
    @Email
    private String email;

    @Column(columnDefinition = "text", nullable = false)
    private String password_hash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;



    public String getCompleteName() {
        return first_name + " " + last_name;
    }

    public User(String email, String password_hash) {
        this.email = email;
        this.password_hash = password_hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
