package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObjects;
import Caprish.Model.imp.users.Staff;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity(name="business")
@NoArgsConstructor
@Getter
@Setter
public class Business extends MyObjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_business;
    @Column(unique=true,columnDefinition="TEXT",nullable=false)
    private String bussiness_name;
    @Column(nullable=false,columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String slogan;
    @OneToOne
    @JoinColumn(nullable = false,name="id_staff")
    private Staff id_staff;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String address;
    @Column(nullable = false)
    @NotBlank(message="El cuit no puede estar vacio")
    private int tax;


//    String sql = "CREATE TABLE business (\n" +
//            "    id_business INT PRIMARY KEY AUTO_INCREMENT,\n" +
//            "    business_name VARCHAR(100) NOT NULL UNIQUE,\n" +
//            "    description TEXT NOT NULL,\n" +
//            "    slogan TEXT NOT NULL,\n" +
//            "    id_staff INT,\n" +
//            "    address VARCHAR(50) NOT NULL,\n" +
//            "    tax INT,\n" +
//            "    FOREIGN KEY (id_staff) REFERENCES bosses(id_staff)    \n" +
//            ");";
















    /*
-- 2) Clientes
    CREATE TABLE client (
            user_id BIGINT PRIMARY KEY,
            loyalty_points INT DEFAULT 0,
            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3) Staff
    CREATE TABLE staff (
            user_id BIGINT PRIMARY KEY,
            department VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4) Jefes (boss)
    CREATE TABLE boss (
            user_id BIGINT PRIMARY KEY,
            company_id BIGINT UNIQUE,
            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (company_id) REFERENCES company(id)
            );

-- 5) Admins
    CREATE TABLE admin (
            user_id BIGINT PRIMARY KEY,
            role_level INT,
            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);*/


};
