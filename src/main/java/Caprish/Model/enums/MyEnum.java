package Caprish.Model.enums;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@MappedSuperclass
@NoArgsConstructor
public abstract class MyEnum extends MyObject {
    @NotBlank(message = "El valor no puede estar vacío")
    @Column(nullable = false, unique = true)
    protected String value;

}
