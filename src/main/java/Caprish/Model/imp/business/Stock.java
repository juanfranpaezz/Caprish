package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="stock")
public class Stock extends MyObject {

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false,name="id_product")
    private Product id_product;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false,name="id_branch")
    private Branch id_branch;

    @Column(nullable = false)
    private int quantity;

    public Stock(Product id_product, Branch id_branch, int quantity) {
        this.id_product = id_product;
        this.id_branch = id_branch;
        this.quantity = quantity;
    }

}
