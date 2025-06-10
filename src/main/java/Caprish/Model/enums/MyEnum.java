package Caprish.Model.enums;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class MyEnum {
    @Id
    @Column(name = "id", length = 255, nullable = false)
    protected String id;
    public MyEnum(String id) {
        this.id = id;
    }
}
