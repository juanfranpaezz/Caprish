package Caprish.Model.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="work_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkRole extends MyEnum {
    public WorkRole(String value) {
        super(value);
    }
//    EMPLOYEE, SUPERVISOR;
}