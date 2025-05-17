package Caprish.Model.imp.sales;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.users.Staff;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor
@Entity
@Table(name="sale")
public class Sale  extends MyObject {

    @OneToOne
    @JoinColumn(name="id",nullable = true)//permite nulos
    private Cart id_cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id")
    private Staff id_staff;

    @Column(nullable = false)
    private LocalDate sale_date;

    @Column(nullable = false,precision = 20, scale = 10)
    private BigDecimal total_amount;

    public Sale(Staff id_staff,Cart id_cart, LocalDate sale_date, BigDecimal total_amount) {
        this.id_cart = id_cart;
        this.id_staff = id_staff;
        this.sale_date = sale_date;
        this.total_amount = total_amount;
    }


}




