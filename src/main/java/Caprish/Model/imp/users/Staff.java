package Caprish.Model.imp.users;

import Caprish.Model.imp.business.Business;
import Caprish.Model.enums.WorkRole;
import Caprish.Model.imp.sales.Sale;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "staff")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Staff extends User {

    @ManyToOne(optional = false)
    @JsonBackReference
    @JoinColumn(name = "id_business", nullable = false)
    private Business business;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_work_role", nullable = false)
    private WorkRole workRole;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Sale> sales = new ArrayList<>();

}
