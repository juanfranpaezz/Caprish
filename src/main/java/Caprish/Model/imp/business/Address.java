package Caprish.Model.imp.business;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    @NotBlank(message = "La localidad no puede estar vacía")
    private String localidad;

    @NotBlank(message = "La provincia no puede estar vacía")
    private String provincia;

    @NotBlank(message = "El país no puede estar vacío")
    private String pais;

    public String getFullAddress() {
        return direccion + ", " + localidad + ", " + provincia + ", " + pais;
    }
}
