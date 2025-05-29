package Caprish.Model.imp.sales.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SalesReportDto {

    private Long saleId;
    private LocalDate saleDate;
    private BigDecimal totalAmount;
    private String staffName;
}
