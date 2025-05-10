package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObjects;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Table
@Entity(name = "voucher")
@NoArgsConstructor
@Getter
@Setter
public class Voucher extends MyObjects {

    @ManyToMany
    @Column(nullable = false,name="id_business")
    private List<Business> businesses;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String code;

    @Column(precision = 5,scale = 2)
    private BigDecimal discount_percent;

    @Column(nullable = false)
    private LocalDate valid_from;

    @Column(nullable = false)
    private LocalDate valid_to;

    @Column(nullable = false)
    private LocalDate created_at;

    public Voucher(List<Business> businesses, String code, BigDecimal discount_percent, LocalDate valid_from, LocalDate valid_to, LocalDate created_at) {
        this.businesses = businesses;
        this.code = code;
        this.discount_percent = discount_percent;
        this.valid_from = valid_from;
        this.valid_to = valid_to;
        this.created_at = created_at;
    }

//    String sql = "CREATE TABLE voucher (\n" +
//            "  id_voucher BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
//            "  id_business BIGINT NOT NULL,\n" +
//            "  code VARCHAR(50) UNIQUE NOT NULL,\n" +
//            "  discount_percent DECIMAL(5,2) NOT NULL,\n" +
//            "  valid_from DATE,\n" +
//            "min_amount " +
//            "max_discount" +
//            "  valid_to DATE,\n" +
//            "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
//            "  FOREIGN KEY (business_id)\n" +
//            "    REFERENCES business(id)\n" +
//            "    ON DELETE CASCADE\n" +
//            ");\n";

}
