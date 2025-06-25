package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image extends MyObject {

    private String nombre;

   @Column(nullable = false, columnDefinition = "TEXT")
   private String url;

    private String tipo;

    @Column(nullable = false)
    private Long referenciaId;

    @Column(nullable = false)
    private String entidad;

}