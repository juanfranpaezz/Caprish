package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.business.Business;
import Caprish.Model.enums.WorkRole;
import Caprish.Model.imp.sales.Cart;
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
public class Staff extends MyObject {

    @OneToOne(optional = false)
    @JoinColumn(name = "id_credential", nullable = false, unique = true)
    private Credential credential;

    @ManyToOne(optional = false)
    @JsonBackReference("staff-business")
    private Business business;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_work_role", nullable = false)
    private WorkRole work_role;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cart-staff")
    private List<Cart> carts = new ArrayList<>();

}
