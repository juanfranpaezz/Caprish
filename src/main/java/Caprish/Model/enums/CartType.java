package Caprish.Model.enums;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cart_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartType extends MyEnum {
    public CartType(String value) {
        super(value);
    }
//    PURCHASE, SALE;
}
