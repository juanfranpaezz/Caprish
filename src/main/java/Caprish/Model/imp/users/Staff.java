package Caprish.Model.imp.users;

import Caprish.Model.imp.business.Branch;
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
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.ArrayList;
import java.util.List;

@EntityScan(basePackages = "Caprish.Model.imp.business")
@Entity
@Table(name = "staff")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Staff extends User {

    @ManyToOne(optional = false)
    @JsonBackReference("staff-branch")
    @JoinColumn(name = "id_branch", nullable = false)
    private Branch branch;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_work_role", nullable = false)
    private WorkRole work_role;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cart-staff")
    private List<Cart> carts = new ArrayList<>();

    public Staff(String email, String password_hash) {
        super(email, password_hash);
    }
}
