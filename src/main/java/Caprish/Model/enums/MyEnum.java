package Caprish.Model.enums;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public abstract class MyEnum extends MyObject {
    @Column(nullable = false)
    private String value;

}
