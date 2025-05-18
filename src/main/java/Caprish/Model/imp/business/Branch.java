package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import Caprish.Model.enums.BranchType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name="branch")
public class Branch extends MyObject {

        @ManyToOne(optional = false)
        @JoinColumn(name = "id_business",nullable = false)
        private Business business;

        @Column(unique=true,nullable = false)
        @NotBlank(message="La direccion no puede estar vacia")
        private String address;

        @ManyToOne(optional = false)
        @JoinColumn(name = "id_branch_type",nullable = false)
        private BranchType barnch_type;

        public Branch(String address, Business business, BranchType barnch_type) {
                this.address = address;
                this.business = business;
                this.barnch_type = barnch_type;
        }

//    String sql =    "CREATE TABLE branch (\n" +
//                    "  id_branch BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
//                    "  id_business BIGINT NOT NULL,\n" +
//                    "  address VARCHAR(200) NOT NULL,\n" +
//                    "  branch_type ENUM(SALES_POINT, WAREHOUSE) NOT NULL," +
//                    "  FOREIGN KEY (id_business)\n" +
//                    "  REFERENCES business(id_business)\n" +
//                    "  ON DELETE CASCADE\n" +
//                    ");";
}