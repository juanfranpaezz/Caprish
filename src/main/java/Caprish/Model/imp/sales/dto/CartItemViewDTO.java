package Caprish.Model.imp.sales.dto;


import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class CartItemViewDTO {

    private String productName;
    private int quantity;
}
