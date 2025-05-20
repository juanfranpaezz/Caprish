package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import Caprish.Model.enums.BranchType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name="branch")
public class Branch extends MyObject {

        @ManyToOne(optional = false)
        @JsonBackReference
        @JoinColumn(name = "id_business", nullable = false)
        private Business business;

        @Column(unique=true, nullable=false)
        private String address;

        @ManyToOne(optional = false)
        @JoinColumn(name = "id_branch_type", nullable = false)
        private BranchType branchType;

        @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference
        private List<Stock> stock = new ArrayList<>();
}
