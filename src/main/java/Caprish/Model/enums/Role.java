package Caprish.Model.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
