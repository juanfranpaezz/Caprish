package Caprish.Model.imp.sales;

import Caprish.Model.imp.MyObjects;
import Caprish.Model.imp.business.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_item")
@Getter
@Setter
public class SaleItem extends MyObjects {

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sale")
    private Sale sale;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

//
//
//
//    String sql =    "CREATE TABLE sale_item (\n" +
//                    "  id_sale_item  BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
//                    "  id_sale       BIGINT NOT NULL,\n" +
//                    "  id_product    BIGINT NOT NULL,\n" +
//                    "  quantity      INT NOT NULL CHECK (quantity > 0),\n" +
//                    "  unit_price    DECIMAL(10,2) NOT NULL CHECK (unit_price >= 0),\n" +
//                    "  FOREIGN KEY (id_sale)    REFERENCES sale(id_sale) ON DELETE CASCADE,\n" +
//                    "  FOREIGN KEY (id_product) REFERENCES products(id_product)\n" +
//                    ");";


}
