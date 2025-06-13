package Caprish.Model.enums;


import Caprish.Model.imp.messaging.Message;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
