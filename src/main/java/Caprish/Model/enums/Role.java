package Caprish.Model.enums;

import Caprish.Model.imp.sales.Cart;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="role")
@Getter
@Setter
public class Role extends MyEnum {
    public Role(String id) {
        super(id);
    }

    public Role() {

    }
}
