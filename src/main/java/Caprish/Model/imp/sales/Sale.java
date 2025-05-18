package Caprish.Model.imp.sales;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.users.Staff;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="sale")
public class Sale  extends MyObject {

    @OneToOne
    @JoinColumn(name="id_cart",nullable = true)//permite nulos
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_staff")
    private Staff staff;

    @Column(nullable = false)
    private LocalDate sale_date;

    @Column(nullable = false,precision = 20, scale = 10)
    private BigDecimal total_amount;

    public Sale(Staff staff, Cart cart, LocalDate sale_date, BigDecimal total_amount) {
        this.cart = cart;
        this.staff = staff;
        this.sale_date = sale_date;
        this.total_amount = total_amount;
    }


}




