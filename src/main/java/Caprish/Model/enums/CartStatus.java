package Caprish.Model.enums;

import Caprish.Model.imp.business.Branch;
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
@Table(name="cart_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartStatus extends MyEnum {
    @OneToMany(mappedBy = "cart_status")
    private List<Cart> carts = new ArrayList<>();

    //    OPEN, CONFIRMED;
}