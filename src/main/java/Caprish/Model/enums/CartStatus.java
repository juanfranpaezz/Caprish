package Caprish.Model.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="cart_status")
@Getter
@Setter
public class CartStatus extends MyEnum {
    public CartStatus(String id) {
        super(id);
    }

    public CartStatus() {

    }
}