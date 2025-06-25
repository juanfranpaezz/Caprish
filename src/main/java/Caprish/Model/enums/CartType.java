package Caprish.Model.enums;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="cart_type")
@Getter
@Setter
public class CartType extends MyEnum {
    public CartType(String id) {
        super(id);
    }

    public CartType() {

    }
}
