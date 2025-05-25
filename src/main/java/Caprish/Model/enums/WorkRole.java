package Caprish.Model.enums;

import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.users.Staff;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="work_role")
@Getter
@Setter
public class WorkRole extends MyEnum {
    @OneToMany(mappedBy = "work_role")
    private List<Staff> staff = new ArrayList<>();
    //    EMPLOYEE, SUPERVISOR, BOSS;
}