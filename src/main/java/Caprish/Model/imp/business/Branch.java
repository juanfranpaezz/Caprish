package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import Caprish.Model.enums.BranchType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name="branch")
public class Branch extends MyObject {

        @ManyToOne(optional = false)
        @JoinColumn(name = "id_business",nullable = false)
        private Business business;

        @Embedded
        private Address address;

        @ManyToOne(optional = false)
        @JoinColumn(name = "id_branch_type",nullable = false)
        private BranchType branch_type;

}