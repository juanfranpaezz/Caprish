package Caprish.Model.imp.users;

import Caprish.Model.imp.business.Business;
import Caprish.Model.enums.WorkRole;
import jakarta.persistence.*;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "id",nullable = false)
    private WorkRole work_role;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "id")
    private Business business;

}
