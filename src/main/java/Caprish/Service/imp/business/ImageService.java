package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Image;
import Caprish.Repository.interfaces.business.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.nio.file.*;
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
        if (file.isEmpty()) {
            throw new IOException("El archivo esta vacio");
        }
        Path uploadPath= Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        //generamos el nombre
        String ext= getFileExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + ext;
        Path filePath = uploadPath.resolve(fileName);

        // guardamos el archivo en el directorio
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        //url
        String url= accessUrl + fileName;

        //guardamos en l base de datos
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
                    //convertir la ruta relativa a absoluta
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