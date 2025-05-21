package Caprish.Model.enums;

import Caprish.Model.imp.business.Branch;
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

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="branch_type")
public class BranchType extends MyEnum {

    @OneToMany(mappedBy = "branch_type")
    private List<Branch> branches = new ArrayList<>();
    //    SALES_POINT, WAREHOUSE
}