package Caprish.Model.enums;

import Caprish.Model.imp.business.Branch;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name="branch_type")
public class BranchType extends MyEnum {
    public BranchType(String id) {
        super(id);
    }

    public BranchType() {

    }
}

    //    SALES_POINT, WAREHOUSE
