package Caprish.Model.imp.sales;

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
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCartProduct;

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
    //    String sql =    "CREATE TABLE cart_product (\n" +
//                    "  id_cart_product  BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
//                    "  id_cart          BIGINT NOT NULL,\n" +
//                    "  id_product       BIGINT NOT NULL,\n" +
//                    "  quantity         INT NOT NULL CHECK(quantity > 0),\n" +
//                    "  unit_price       DECIMAL(10,2) NOT NULL CHECK(unit_price >= 0),\n" +
//                    "  FOREIGN KEY (id_cart)    REFERENCES cart(id_cart) ON DELETE CASCADE,\n" +
//                    "  FOREIGN KEY (id_product) REFERENCES products(id_product)\n" +
//                    ");\n";
}





