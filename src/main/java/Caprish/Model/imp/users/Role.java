package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import lombok.*;

import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role extends MyObject {

    @Column(unique = true, nullable = false)
    private String name; // Ej: ROLE_ADMIN, ROLE_CLIENT

}
