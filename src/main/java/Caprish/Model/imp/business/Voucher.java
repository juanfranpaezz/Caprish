package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Table
@Entity(name = "voucher")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Voucher extends MyObject {

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

}
