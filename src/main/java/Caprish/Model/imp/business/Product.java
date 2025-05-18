package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="product")
public class Product extends MyObject {

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false,name="id_business")
    private Business business;

    @Column(nullable=false,columnDefinition = "TEXT")
    private String name;

    private Double bar_code;

    @Column(nullable=false,columnDefinition = "TEXT")
    private String description;

    @Column(nullable=false,precision = 20, scale = 10)
    private BigDecimal price;



    public Product(Business business, String name, Double bar_code, String description, BigDecimal price) {
        this.business = business;
        this.name = name;
        this.bar_code = bar_code;
        this.description = description;
        this.price = price;
    }

}
