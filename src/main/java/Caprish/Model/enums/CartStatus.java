package Caprish.Model.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="cart_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartStatus extends MyEnum {
    public CartStatus(String value) {
        super(value);
    }
//    OPEN, CONFIRMED;
}