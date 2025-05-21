package Caprish.Model.imp.admin;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.business.Business;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="businness_report")
public class BusinessReport extends MyObject {

    @ManyToOne(optional = false)
    @JsonBackReference
    @JoinColumn(name = "id_business", nullable = false)
    private Business business;

    @Column(nullable=false)
    private LocalDate generatedAt;

    @Column(columnDefinition= "TEXT")
    private String description;

    @NotBlank(message="La descripción no puede estar vacía")
    private String about;
}

