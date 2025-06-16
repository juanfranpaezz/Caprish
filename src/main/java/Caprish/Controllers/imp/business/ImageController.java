package Caprish.Controllers.imp.business;

import Caprish.Model.imp.business.Image;
import Caprish.Service.imp.business.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@Validated
@Tag(name = "Imágenes", description = "Gestión de imágenes asociadas a entidades del sistema")
public class ImageController {

    @Autowired
    private  ImageService imageService;


    @Operation(
            summary = "Obtener imágenes por entidad y referencia",
            description = "Devuelve una lista de imágenes vinculadas a una entidad específica por su ID de referencia"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de imágenes devuelta exitosamente")
    })
    @GetMapping("/byEntity")
    public ResponseEntity<List<Image>> getImagesByEntity(
            @RequestParam String entidad,
            @RequestParam Long referenciaId)
    {
        List<Image> images = imageService.findByEntidadAndReferenciaId(entidad, referenciaId);
        return ResponseEntity.ok(images);
    }

    @Operation(
            summary = "Subir una imagen",
            description = "Permite subir una imagen asociada a una entidad del sistema (por ejemplo, un producto o usuario)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Imagen guardada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error al guardar la imagen")
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("entidad") String entidad,
            @RequestParam("referenciaId") Long referenciaId,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "tipo", required = false) String tipo
    ) {
        try {
            Image image = imageService.saveImage(file, entidad, referenciaId, nombre, tipo);
            return ResponseEntity.ok(image);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(
            summary = "Eliminar imagen por ID",
            description = "Elimina una imagen del sistema utilizando su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Imagen eliminada exitosamente")
    })
    @DeleteMapping("/view/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
       imageService.DeleteById(id);
       return ResponseEntity.noContent().build();
    }
}
