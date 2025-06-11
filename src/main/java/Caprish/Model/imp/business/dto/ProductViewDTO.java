package Caprish.Model.imp.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class ProductViewDTO {
    private Long idProduct;
    private String productName;
    private Double productPrice;
    private String businessName;
    private Integer stock;


}
