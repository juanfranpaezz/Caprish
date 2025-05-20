package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Image;
import Caprish.Repository.interfaces.business.ImageRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService extends MyObjectGenericService<Image, ImageRepository> {

    protected ImageService(ImageRepository repository) {
        super(repository);
    }

    public List<Image> findByEntidadAndReferenciaId(String entidad, Long referenciaId) {
        return repository.findByEntidadAndReferenciaId(entidad, referenciaId);
    }
}
