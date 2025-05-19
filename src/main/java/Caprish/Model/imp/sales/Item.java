package Caprish.Model.imp.sales;

import Caprish.Model.imp.MyObject;
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
@Table(name = "item")
public class Item extends MyObject {

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cart")
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(nullable = false)
    private int quantity;

}





