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
   private String url; // se guarda la url de donde esta guardada la imagen

    private String tipo; // e.g. "perfil", "producto", etc.

    @Column(nullable = false)
    private Long referenciaId; // id del producto, usuario, etc.

    @Column(nullable = false)
    private String entidad; // "Producto", "Usuario", etc.

}