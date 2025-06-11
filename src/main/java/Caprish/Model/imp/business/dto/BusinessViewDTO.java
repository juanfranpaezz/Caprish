package Caprish.Model.imp.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class BusinessViewDTO {
    private Long id;
    private String businessName;
    private String description;
    private String slogan;
    private Integer tax;

}
