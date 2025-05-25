package Caprish.Repository.interfaces.business;

import Caprish.Model.imp.business.Image;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import java.util.List;

public interface ImageRepository extends MyObjectGenericRepository<Image> {

    List<Image> findByEntidadAndReferenciaId(String entidad, Long referenciaId);
}
