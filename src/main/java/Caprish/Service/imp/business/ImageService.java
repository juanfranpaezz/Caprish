package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Image;
import Caprish.Repository.interfaces.business.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;


@Service
public class ImageService {

    @Value("${image.upload.dir}")
    private String uploadDir;

    @Autowired
    private ImageRepository imageRepository;

    @Value("${image.access.url}")
    private String accessUrl;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> findByEntidadAndReferenciaId(String entidad, Long referenciaId) {
        return imageRepository.findByEntidadAndReferenciaId(entidad, referenciaId);
    }


    public Image saveImage(MultipartFile file, String entidad, Long referenciaId, String nombre, String tipo) throws IOException {
        if (file==null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo esta vacio");
        }
        String ext = getFileExtension(file.getOriginalFilename()).toLowerCase();
        if (!List.of(".jpg",".png").contains(ext)) {
            throw new IllegalArgumentException("Tipo de archivo no permitido. Solo se permiten imágenes.");
        }

        if (entidad == null || entidad.isBlank()) {
            throw new IllegalArgumentException("La entidad no puede estar vacía.");
        }

        if (referenciaId == null || referenciaId <= 0) {
            throw new IllegalArgumentException("La referencia debe ser un ID válido.");
        }


        Path uploadPath= Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = UUID.randomUUID().toString() + ext;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        String url= accessUrl + fileName;
        Image image = new Image();
        image.setNombre(nombre!= null ? nombre : file.getOriginalFilename());
        image.setUrl(url);
        image.setEntidad(entidad.toLowerCase());
        image.setReferenciaId(referenciaId);
        image.setTipo(tipo!= null ? tipo :"default");

        return imageRepository.save(image);
    }

    public void DeleteById(Long id) {
        Image image =imageRepository.findById(id).orElse(null);
        if(image != null) {
            String imageUrl= image.getUrl();
            if(imageUrl != null && !imageUrl.isEmpty()) {
                try{
                    String fileName= imageUrl.substring(imageUrl.lastIndexOf("/")+1);
                    Path filePath = Paths.get(uploadDir, fileName);
                    Files.deleteIfExists(filePath);
                }catch (IOException e){
                    System.err.println("error al eliminar el archivo" + e.getMessage());
                }
            }
            imageRepository.deleteById(id);
        }
    }

    private String getFileExtension(String fileName) {
        if(fileName!=null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }
}