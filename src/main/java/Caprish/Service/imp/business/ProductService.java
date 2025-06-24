package Caprish.Service.imp.business;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.business.Product;
import Caprish.Model.imp.business.dto.ProductViewDTO;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.MyObjectGenericService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService extends MyObjectGenericService<Product, ProductRepository, ProductService> {

    @Autowired
    private ImageService imageService;

    @Autowired
    private BusinessRepository businessRepository;

    protected ProductService(ProductRepository repository) {
        super(repository);
    }

    public List<ProductViewDTO> getProductByBusinessName(String businessName) {
        return repository.getProductByBusiness(businessName)
                .stream()
                .map(obj -> new ProductViewDTO(
                        ((Number) obj[0]).longValue(),
                        (String) obj[1],                           // clientName (first + last)
                        ((Number) obj[2]).doubleValue(),                          // cartType
                        (String) obj[3],                           // cartStatus
                        (Integer) obj[4]

                ))
                .collect(Collectors.toList());
    }

    @Override
    protected void verifySpecificAttributes(Product entity) {

        if (entity.getName().length() < 3 || entity.getName().length() > 100) {
            throw new InvalidEntityException("El nombre del producto debe tener entre 3 y 100 caracteres.");
        }

        boolean nombreDuplicado = repository
                .findByNameAndBusiness_Id(entity.getName(), entity.getBusiness().getId())
                .stream()
                .anyMatch(existing -> !existing.getId().equals(entity.getId())); // ignoramos el mismo si es actualización

        if (nombreDuplicado) {
            throw new InvalidEntityException("Ya existe un producto con ese nombre en el negocio.");
        }

        if (entity.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidEntityException("El precio debe ser mayor a cero.");
        }

        if (entity.getBusiness() == null || entity.getBusiness().getId() == null) {
            throw new InvalidEntityException("El producto debe estar asociado a un negocio válido.");
        }

        if (!businessRepository.existsById(entity.getBusiness().getId())) {
            throw new InvalidEntityException("El negocio asociado no existe.");
        }

    }
    public Product findByName(String name)throws EntityNotFoundException{
        return repository.findProductByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }
    public Long findIdByName(String name){
        return repository.findIdProductByName(name);
    }


    public Product findByIdWithImages(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product != null) {
            product.setImagenes(imageService.findByEntidadAndReferenciaId("Producto", product.getId()));
        }
        return product;
    }
}
