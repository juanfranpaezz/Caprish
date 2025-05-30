package Caprish.Model.enums;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
@NoArgsConstructor
public abstract class MyEnum {
    @Id
    protected String id;
}
