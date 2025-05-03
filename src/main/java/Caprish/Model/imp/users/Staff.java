package Caprish.Model.imp.users;

import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.users.enums.WorkRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity(name="staff")
@NoArgsConstructor
@Getter
@Setter
public class Staff extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_staff;

    @Column(columnDefinition = "text", nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String first_name;

    @Column(columnDefinition = "text", nullable = false)
    @NotBlank(message = "El apellido no puede estar vacío")
    private String last_name;

    @Column(columnDefinition = "text", nullable = false)
    @NotBlank(message = "La contrasena no puede estar vacía")
    private String password;

    @Column(unique=true, columnDefinition = "text", nullable = false)
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email no es válido")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkRole work_role;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_business")
    private Business business;

    public Staff(Long id, String email, String password_hash, Long id_staff, String first_name, String last_name, String password, String email1, WorkRole work_role, Business business) {
        super(id, email, password_hash);
        this.id_staff = id_staff;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.email = email1;
        this.work_role = work_role;
        this.business = business;
    }

}
