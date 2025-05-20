package Caprish.Model.enums;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "El valor no puede estar vacío")
    @Column(nullable = false)
    private final String value;

    public MyEnum(String value) {
        this.value = value;
    }
}
