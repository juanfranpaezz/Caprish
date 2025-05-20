package Caprish.Model.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="sender_type")
@Getter
@Setter
public class SenderType extends MyEnum {
    public SenderType(String value) {
        super(value);
    }

// ADMIN, CLIENT, STAFF
}
