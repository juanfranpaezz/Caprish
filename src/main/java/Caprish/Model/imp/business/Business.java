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

    public Business(String bussiness_name, String description, String slogan, Staff id_staff, String address, int tax) {
        this.bussiness_name = bussiness_name;
        this.description = description;
        this.slogan = slogan;
        this.id_staff = id_staff;
        this.address = address;
        this.tax = tax;
    }

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



};
