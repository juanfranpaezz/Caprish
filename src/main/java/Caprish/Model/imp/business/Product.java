package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;
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
    @JsonBackReference("product-business")
    @JoinColumn(name="id_business")
    private Business business;

    @NotBlank(message = "El texto no puede estar vacío")
    @Column(nullable=false,columnDefinition = "TEXT")
    private String name;

    @NotBlank(message = "El texto no puede estar vacío")
    @Column(nullable=false,columnDefinition = "TEXT")
    private String description;

    @Column(nullable=false,precision = 20, scale = 10)
    private BigDecimal price;

    @Transient
    private List<Image> imagenes = new ArrayList<>();

    @Column(nullable = false)
    private Integer stock;

}
