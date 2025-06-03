package Caprish.Service.imp.business;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    @Override
    protected void verifySpecificAttributes(Product entity) {

        if (entity.getName().length() < 3 || entity.getName().length() > 100) {
            throw new InvalidEntityException("El nombre del producto debe tener entre 3 y 100 caracteres.");
        }

        boolean nombreDuplicado = repository
                .findByNombreAndBusinessId(entity.getName(), entity.getBusiness().getId())
                .stream()
                .anyMatch(existing -> !existing.getId().equals(entity.getId())); // ignoramos el mismo si es actualización

        if (nombreDuplicado) {
            throw new InvalidEntityException("Ya existe un producto con ese nombre en el negocio.");
        }

        if (entity.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidEntityException("El precio debe ser mayor a cero.");
        }

        // REVISAR ESTO
        if (entity.getBusiness() == null || entity.getBusiness().getId() == null) {
            throw new InvalidEntityException("El producto debe estar asociado a un negocio válido.");
        }

        if (!businessRepository.existsById(entity.getBusiness().getId())) {
            throw new InvalidEntityException("El negocio asociado no existe.");
        }

        if (entity.getStock() != null) {
            entity.getStock().forEach(s -> {
                if (s.getQuantity() < 0) {
                    throw new InvalidEntityException("El stock no puede tener cantidades negativas.");
                }
            });
        }
    }



    public Product findByIdWithImages(Long id) {
        Product product = repository.findById(id).orElse(null);
        if (product != null) {
            product.setImagenes(imageService.findByEntidadAndReferenciaId("Producto", product.getId()));
        }
        return product;
    }
}
