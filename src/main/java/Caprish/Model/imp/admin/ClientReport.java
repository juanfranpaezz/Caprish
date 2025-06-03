package Caprish.Model.imp.admin;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.sales.Cart;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="client_report")
public class ClientReport extends MyObject {

    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;        // fecha principal

    @Column(name = "report_date_mod")
    private LocalDate reportDateModified; // segunda fecha

    @Column(name = "report_data", columnDefinition = "TEXT")
    private String reportData;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_cart", nullable = false)
    private Cart cart;
}
