package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
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
public class Business extends MyObject {

    @Column(unique=true,columnDefinition="TEXT",nullable=false)
    private String bussiness_name;

    @Column(nullable=false,columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String slogan;

    @OneToOne(optional = false)
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


};
