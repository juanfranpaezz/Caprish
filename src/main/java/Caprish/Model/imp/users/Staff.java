package Caprish.Model.imp.users;

import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.users.enums.WorkRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="staff")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Staff extends User {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkRole work_role;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "id_business")
    private Business business;

}
