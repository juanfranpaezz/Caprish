package Caprish.Model.imp.users;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table (name="client")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client extends User {

    @Column(unique=true,nullable = false)
    private Integer phone;

    @NotBlank(message = "El tax no puede ser nulo")
    @Column(unique=true,nullable = false)
    private String tax;

}
