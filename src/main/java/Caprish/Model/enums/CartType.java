package Caprish.Model.enums;


import Caprish.Model.imp.sales.Cart;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cart_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartType extends MyEnum {
    @OneToMany(mappedBy = "cart_type")
    private List<Cart> carts = new ArrayList<>();
    //    PURCHASE, SALE;
}
