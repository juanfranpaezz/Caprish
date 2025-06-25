package Caprish.Model.imp.sales;

import Caprish.Model.imp.MyObject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import Caprish.Model.imp.business.Product;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name="item")
public class Item extends MyObject {
    @ManyToOne(optional = false)
    @JsonBackReference("item-cart")
    @JoinColumn(name="id_cart")
    private Cart cart;

    @ManyToOne(optional = false)
    @JsonBackReference("item-product")
    @JoinColumn(name="id_product")
    private Product product;

    @Column(nullable = false)
    private int quantity;
}
