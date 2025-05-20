package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObject;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image extends MyObject {

    private String nombre;

    @Lob
    @Column(columnDefinition = "LONGBLOB", nullable = false)
    private byte[] datos;

    private String tipo; // e.g. "perfil", "producto", etc.

    private Long referenciaId; // id del producto, usuario, etc.

    private String entidad; // "Producto", "Usuario", etc.

}