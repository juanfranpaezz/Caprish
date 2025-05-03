package Caprish.Model.imp.admin;

import Caprish.Model.imp.sales.Sale;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="client_report")
public class ClientReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @Column(name="id_sale" ,nullable = false)
    private Sale id_sale;
    @Column (nullable = false)
    private LocalDate report_date;
    @Column (name = "TEXT")
    private String report_data;

    public ClientReport(LocalDate report_date, Sale id_sale, String report_data) {
        this.report_date = report_date;
        this.id_sale = id_sale;
        this.report_data = report_data;
    }
}
