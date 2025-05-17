package Caprish.Model.enums;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class MyEnum extends MyObject {
    @Column(nullable = false)
    private String value;

}
