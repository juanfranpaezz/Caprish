package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

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

    @NotBlank(message = "El texto no puede estar vacío")
    @Column(nullable=false,columnDefinition = "TEXT")
    private String name;

    private Double bar_code;

    @NotBlank(message = "El texto no puede estar vacío")
    @Column(nullable=false,columnDefinition = "TEXT")
    private String description;

    @Column(nullable=false,precision = 20, scale = 10)
    private BigDecimal price;



}
