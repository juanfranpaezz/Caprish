package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObjects;
import Caprish.Model.imp.users.Staff;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa una entidad de negocio que contiene información sobre el negocio.
 * Esta clase está mapeada a la tabla "business" en la base de datos.
 *
 * @author Matias
 * @version 1.0
 */
@Table
@Entity(name="business")
@NoArgsConstructor
@Getter
@Setter
public class Business extends MyObjects {

    /** Nombre del negocio. Este campo debe ser único y no puede ser nulo. */
    @Column(unique=true,columnDefinition="TEXT",nullable=false)
    private String bussiness_name;

    /**Descripción del negocio. Este campo no puede ser nulo.*/
    @Column(nullable=false,columnDefinition = "TEXT")
    private String description;

    /**Eslogan del negocio. Este campo no puede ser nulo.*/
    @Column(nullable = false,columnDefinition = "TEXT")
    private String slogan;

    /**
     * Relación uno a uno con la entidad Staff, indicando que el negocio tiene un único jefe.
     * Este campo es obligatorio.
     */
    @OneToOne(optional = false)
    @JoinColumn(nullable = false,name="id_boss")
    private Staff id_boss;

    /**Dirección del negocio. Este campo no puede ser nulo.*/
    @Column(nullable = false,columnDefinition = "TEXT")
    private String address;

    /**
     * Número de identificación tributaria del negocio. Este campo no puede estar vacío.
     */
    @Column(nullable = false)
    @NotBlank(message="El cuit no puede estar vacio")
    private int tax;

    /**
     * Constructor para crear una nueva entidad de Business.
     *
     * @param bussiness_name Nombre del negocio.
     * @param description Descripción del negocio.
     * @param slogan Eslogan del negocio.
     * @param id_boss El staff encargado del negocio.
     * @param address Dirección del negocio.
     * @param tax Número de identificación tributaria del negocio.
     */
    public Business(String bussiness_name, String description, String slogan, Staff id_boss, String address, int tax) {
        this.bussiness_name = bussiness_name;
        this.description = description;
        this.slogan = slogan;
        this.id_boss = id_boss;
        this.address = address;
        this.tax = tax;
    }

};











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