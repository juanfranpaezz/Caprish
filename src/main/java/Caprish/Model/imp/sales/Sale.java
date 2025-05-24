package Caprish.Model.imp.sales;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Staff;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="sale")
public class Sale extends MyObject {
    @OneToOne(optional = false)
    @JoinColumn(name="id_cart")
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_staff")
    @JsonBackReference("sale-staff")
    private Staff staff;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_client", nullable = false)
    @JsonBackReference("sale-client")
    private Client client;

    @Column(nullable = false)
    private LocalDate sale_date;

    @Column(nullable = false,precision = 20, scale = 10)
    private BigDecimal total_amount;



}
