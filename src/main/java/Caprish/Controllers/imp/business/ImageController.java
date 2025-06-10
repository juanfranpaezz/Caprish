package Caprish.Controllers.imp.business;

import Caprish.Model.imp.business.Image;
import Caprish.Service.imp.business.ImageService;
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
public class ImageController {

    @Autowired
    private  ImageService imageService;



    @GetMapping("/byEntity")
    public ResponseEntity<List<Image>> getImagesByEntity(
            @RequestParam String entidad,
            @RequestParam Long referenciaId)
    {
        List<Image> images = imageService.findByEntidadAndReferenciaId(entidad, referenciaId);
        return ResponseEntity.ok(images);
    }

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


    @DeleteMapping("/view/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
       imageService.DeleteById(id);
       return ResponseEntity.noContent().build();
    }
}
