package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.users.Staff;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity(name="business")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Business extends MyObject {

    @NotBlank(message = "El texto no puede estar vacío")
    @Column(unique=true,columnDefinition="TEXT",nullable=false)
    private String bussiness_name;

    @NotBlank(message = "El texto no puede estar vacío")
    @Column(nullable=false,columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "El texto no puede estar vacío")
    @Column(nullable = false,columnDefinition = "TEXT")
    private String slogan;

    @Column(nullable = false)
    @NotBlank(message="El cuit no puede estar vacio")
    private int tax;

};
