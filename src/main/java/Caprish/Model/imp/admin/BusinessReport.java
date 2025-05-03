package Caprish.Model.imp.admin;

import Caprish.Model.imp.MyObjects;
import Caprish.Model.imp.business.Business;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="businness_report")
public class BusinessReport extends MyObjects {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id_business_report;
    @Column (nullable=false)
    @ManyToOne
    @JoinColumn(name = "id_business", nullable = false)  // FK en la tabla business_report
    private Business id_business; //tiene que ser un objeto
    @Column (nullable=false)
    private LocalDate generated_at;
    @Column (columnDefinition= "TEXT")
    private String description;
    @NotBlank(message="la descripcion no puede estar vacia")
    private String about;

    public BusinessReport(Business id_business, LocalDate generated_at, String description, String about) {
        this.id_business = id_business;
        this.generated_at = generated_at;
        this.description = description;
        this.about = about;
    }
}
