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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="client_report")
public class ClientReport extends MyObject {

    @OneToOne(optional = false)
    @JoinColumn(name = "id_cart", nullable = false)
    private Cart cart;

    @Column(nullable = false)
    private LocalDate reportDate;

    @Column(columnDefinition = "TEXT")
    private String reportData;
}

