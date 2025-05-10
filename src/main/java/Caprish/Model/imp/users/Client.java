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

    @Column(columnDefinition = "text",nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String first_name;

    @Column(columnDefinition = "text",nullable = false)
    @NotBlank(message = "El apellido no puede estar vacío")
    private String last_name;

    @Column(unique=true,nullable = false)
    private Integer phone;

}
