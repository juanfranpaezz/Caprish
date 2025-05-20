package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Transient
    private List<Image> imagenes = new ArrayList<>();

}
