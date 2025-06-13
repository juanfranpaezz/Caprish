package Caprish.Model.imp.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class StaffViewDTO {
    private Long idStaff;
    private String firstName;
    private String lastName;
    private String role;
    private Long idBusiness;
    private String businessName;
}
