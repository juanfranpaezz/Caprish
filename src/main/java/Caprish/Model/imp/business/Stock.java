package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
