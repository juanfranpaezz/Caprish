package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObjects;
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
public class Product extends MyObjects {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false,name="id_business")
    private Business business;

    @Column(nullable=false,columnDefinition = "TEXT")
    private String name;

    @Column(nullable=false)
    private double bar_code;

    @Column(nullable=false,columnDefinition = "TEXT")
    private String description;

    @Column(nullable=false,precision = 20, scale = 10)
    private BigDecimal price;

//    String sql =    "CREATE TABLE product (\n" +
//                    "  id_product BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
//                    "  id_business BIGINT NOT NULL,\n" +
//                    "  name VARCHAR(150) NOT NULL,\n" +
//                    "  bar-code nosequetipodedato NOT NULL,\n" +
//                    "  description TEXT,\n" +
//                    "  price DECIMAL(12,2) NOT NULL,\n" +
//                    "  FOREIGN KEY (id_business)\n" +
//                    "    REFERENCES business(id_business)\n" +
//                    "    ON DELETE CASCADE\n" +
//                    ");";


    public Product(Business business, String name, double bar_code, String description, BigDecimal price) {
        this.business = business;
        this.name = name;
        this.bar_code = bar_code;
        this.description = description;
        this.price = price;
    }
}
