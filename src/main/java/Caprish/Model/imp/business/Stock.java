package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference("stock-product")
    @JoinColumn(name="id_product")
    private Product product;

    @ManyToOne(optional = false)
    @JsonBackReference("stock-branch")
    @JoinColumn(name="id_branch")
    private Branch branch;

    @Column(nullable = false)
    private int quantity;
}
