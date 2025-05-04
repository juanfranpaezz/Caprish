package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObjects;
import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Products  extends MyObjects {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false,name="id_business")
    private Business business;
    @Column(nullable=false,columnDefinition = "TEXT")
    private String name;
    @Column(nullable=false)


    String sql =    "CREATE TABLE product (\n" +
                    "  id_product BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "  id_business BIGINT NOT NULL,\n" +
                    "  name VARCHAR(150) NOT NULL,\n" +
                    "  bar-code nosequetipodedato NOT NULL,\n" +
                    "  description TEXT,\n" +
                    "  price DECIMAL(12,2) NOT NULL,\n" +
                    "  FOREIGN KEY (id_business)\n" +
                    "    REFERENCES business(id_business)\n" +
                    "    ON DELETE CASCADE\n" +
                    ");";


}
