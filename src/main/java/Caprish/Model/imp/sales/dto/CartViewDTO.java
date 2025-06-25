package Caprish.Model.imp.sales.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class CartViewDTO {
    private Long idCart;
    private String clientName;
    private String cartType;
    private String cartStatus;
    private String staffName;
    private Long idBusiness;
}
