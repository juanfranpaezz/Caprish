package Caprish.Model.enums;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="sender_type")
public class SenderType extends MyEnum {

    public SenderType(String id) {
        super(id);
    }

    public SenderType() {

    }
}
