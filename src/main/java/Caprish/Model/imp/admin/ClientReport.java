package Caprish.Model.imp.admin;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.sales.Sale;
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
@Table(name="client_report")
public class ClientReport extends MyObject {

    @ManyToOne (optional = false)
    @JoinColumn(name = "id_sale", nullable = false)
    private Sale id_sale;

    @Column (nullable = false)
    private LocalDate report_date;

    @NotBlank(message = "El texto no puede estar vacío")
    @Column (name = "TEXT")
    private String report_data;

}
