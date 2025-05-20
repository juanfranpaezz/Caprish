package Caprish.Controllers.imp.business;

import Caprish.Model.imp.business.Image;
import Caprish.Service.imp.business.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // ✅ Obtener todas las imágenes de una entidad por ID
    @GetMapping("/byEntity")
    public ResponseEntity<List<Image>> getImagesByEntity(
            @RequestParam String entidad,
            @RequestParam Long referenciaId) {
        List<Image> images = imageService.findByEntidadAndReferenciaId(entidad, referenciaId);
        return ResponseEntity.ok(images);
    }

    // ✅ Subir una imagen genérica desde Postman o formulario
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("entidad") String entidad,
            @RequestParam("referenciaId") Long referenciaId,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "tipo", required = false) String tipo
    ) {
        try {
            Image image = new Image();
            image.setDatos(file.getBytes());
            image.setNombre(nombre != null ? nombre : file.getOriginalFilename());
            image.setEntidad(entidad.toLowerCase());
            image.setReferenciaId(referenciaId);
            image.setTipo(tipo != null ? tipo : "default");

            Image saved = imageService.save(image);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ✅ Eliminar imagen por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Obtener imagen binaria para mostrarla (opcional)
    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> viewImage(@PathVariable Long id) {
        return imageService.findById(id)
                .map(img -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Cambiar a PNG si corresponde
                        .body(img.getDatos()))
                .orElse(ResponseEntity.notFound().build());
    }
}
