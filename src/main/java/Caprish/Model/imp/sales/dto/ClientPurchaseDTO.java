package Caprish.Model.imp.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
public class ClientPurchaseDTO {
    private Long cartId;
    private String clientName;
    private String cartType;
    private String cartStatus;
    private String staffName;
    private String businessName;
    private Double totalAmount;
    private List<CartItemViewDTO> items;
}