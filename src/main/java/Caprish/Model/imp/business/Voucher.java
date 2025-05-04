package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObjects;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Table
@Entity(name = "voucher")
@NoArgsConstructor
public class Voucher extends MyObjects {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_voucher;

    String sql = "CREATE TABLE voucher (\n" +
            "  id_voucher BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
            "  id_business BIGINT NOT NULL,\n" +
            "  code VARCHAR(50) UNIQUE NOT NULL,\n" +
            "  discount_percent DECIMAL(5,2) NOT NULL,\n" +
            "  valid_from DATE,\n" +
            "min_amount " +
            "max_discount" +
            "  valid_to DATE,\n" +
            "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
            "  FOREIGN KEY (business_id)\n" +
            "    REFERENCES business(id)\n" +
            "    ON DELETE CASCADE\n" +
            ");\n";

}
