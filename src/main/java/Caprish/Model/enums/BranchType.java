package Caprish.Model.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="branch_type")
public class BranchType extends MyEnum {

    //    SALES_POINT, WAREHOUSE
}