package Caprish.Model.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;


@Entity
@Table(name="work_role")
@Getter
@Setter
public class WorkRole extends MyEnum {
    //    EMPLOYEE, SUPERVISOR;
}