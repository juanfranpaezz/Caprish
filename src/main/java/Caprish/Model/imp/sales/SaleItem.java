package Caprish.Model.imp.sales;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.business.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem extends MyObject {

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sale")
    private Sale sale;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

}
