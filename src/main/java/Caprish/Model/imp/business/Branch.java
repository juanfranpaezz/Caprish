//package Caprish.Model.imp.business;
//
//import Caprish.Model.imp.MyObject;
//import Caprish.Model.enums.BranchType;
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Setter
//@Getter
//@Table(name="branch")
//public class Branch extends MyObject {
//
//        @ManyToOne(optional = false)
//        @JsonBackReference("branch-business")
//        @JoinColumn(name = "id_business", nullable = false)
//        private Business business;
//
//        @ManyToOne(optional = false)
//        @JoinColumn(
//                name = "id_branch_type",
//                referencedColumnName = "id",
//                nullable = false,
//                columnDefinition = "VARCHAR(255)"
//        )
//        @JsonIgnoreProperties
//        private BranchType branch_type;
//
//
//}