package Caprish.Model.enums;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "El valor no puede estar vacío")
    @Column(nullable = false)
    private final String value;

    public MyEnum(String value) {
        this.value = value;
    }
}
