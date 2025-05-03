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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(nullable = false,name = "id_business")
    private Business business;

//
//    String sql = "CREATE TABLE staff (\n" +
//            "    id_staff INT PRIMARY KEY AUTO_INCREMENT,\n" +
//            "    first_name VARCHAR(50) NOT NULL,\n" +
//            "    last_name VARCHAR(50) NOT NULL,\n" +
//            "    email VARCHAR(100) NOT NULL UNIQUE,\n" +
//            "    pass VARCHAR(255) NOT NULL,\n" +
//            "    work_role ENUM('supervisor', 'employee'),\n" +
//            "    id_business INT,    \n" +
//            "    FOREIGN KEY (id_business) REFERENCES empresas(id_business)\n" +
//            ");";


    /*tiene  Enum workrole*/
}
