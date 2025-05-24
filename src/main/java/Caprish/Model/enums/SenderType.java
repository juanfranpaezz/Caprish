package Caprish.Model.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="sender_type")
@Getter
@Setter
public class SenderType extends MyEnum {

// ADMIN, CLIENT, STAFF
}
