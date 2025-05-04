package Caprish.Model.imp.sales;

import Caprish.Model.imp.MyObjects;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor
@Entity
@Table(name="sale")
public class Sale  extends MyObjects {
    @Id
    @GeneratedValue (strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="id_cart")
    private ShoppingCart id_cart;

    @Column(nullable = false)
    private LocalDate sale_date;

    @Column(nullable = false,precision = 20, scale = 10)
    private BigDecimal total_amount;

    public Sale(ShoppingCart id_cart, LocalDate sale_date, BigDecimal total_amount) {
        this.id_cart = id_cart;
        this.sale_date = sale_date;
        this.total_amount = total_amount;
    }

    //
//    String sql =    "CREATE TABLE sale (\n" +
//                    "  id_sale        BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
//                    "  id_cart   BIGINT NOT NULL,\n" +
//                    "  sale_date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
//                    "  total_amount   DECIMAL(12,2) NOT NULL,\n" +
//                    "  FOREIGN KEY (id_cart) REFERENCES cart(id_cart) ON DELETE RESTRICT\n" +
//                    ");";

}




