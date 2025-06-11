package Caprish.Model.imp.users.dto;

import Caprish.Model.enums.Role;
import Caprish.Model.enums.WorkRole;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.Staff;
import lombok.Data;
import Caprish.Model.imp.business.*;

@Data
public class StaffResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private Role role;
    private WorkRole workRole;
    private String businessName;

    // Datos de la sucursal (sin 'name')
    private Long branchId;
    private Address address; // Mostrar dirección completa

    public StaffResponseDTO(Staff staff) {
        this.id = staff.getId();
        Credential credential = staff.getCredential();
        this.firstName = credential.getFirst_name();
        this.lastName = credential.getLast_name();
        this.email = credential.getUsername();
        this.enabled = credential.isEnabled();
        this.role = credential.getRole();
        this.workRole = staff.getWork_role();

        // Datos de Branch
        Branch branch = staff.getId_branch();
        this.branchId = branch.getId();
        this.businessName = branch.getBusiness().getBusinessName();

        this.address = branch.getAddress();
    }
}
