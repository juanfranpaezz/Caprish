package Caprish.Model.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name="branch_type")
public class BranchType extends MyEnum {
//    SALES_POINT, WAREHOUSE
}