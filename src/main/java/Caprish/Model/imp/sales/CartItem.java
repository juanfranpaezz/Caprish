package Caprish.Model.imp.sales;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import Caprish.Model.imp.business.Product;
import java.math.BigDecimal;
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem extends MyObject {

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cart")
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public CartItem(Cart cart, Product product, int quantity, BigDecimal unitPrice) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

}





