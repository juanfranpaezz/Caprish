package Caprish.Model.imp.admin;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.business.Business;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="businness_report")
public class BusinessReport extends MyObject {

    @ManyToOne (optional = false)
    @JoinColumn(name = "id_business", nullable = false)  // FK en la tabla business_report
    private Business id_business; //tiene que ser un objeto

    @Column (nullable=false)
    private LocalDate generated_at;

    @NotBlank(message = "El texto no puede estar vacío")
    @Column (columnDefinition= "TEXT")
    private String description;

    @NotBlank(message="la descripcion no puede estar vacia")
    private String about;


}
