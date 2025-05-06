package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObjects;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="stock")
public class Stock extends MyObjects {

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

//      String sql =    "CREATE TABLE stock_history (\n" +
//                    "  id_stock BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
//                    "  id_product BIGINT NOT NULL,\n" +
//                    "  id_branch BIGINT NOT NULL,\n" +
//                    "  quantity INT NOT NULL,\n" +
//                    "    FOREIGN KEY (id_product)\n" +
//                    "    REFERENCES stock(id_product)\n" +
//                    "    FOREIGN KEY (id_branch)\n" +
//                    "    REFERENCES branch(id_branch)\n" +
//                    "    ON DELETE CASCADE\n" +
//                    ");\n";
}
