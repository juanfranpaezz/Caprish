package Caprish.Service.imp.business;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.business.Address;
import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.MyObjectGenericService;
import Caprish.Service.others.GoogleGeocodingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BusinessService extends MyObjectGenericService<Business, BusinessRepository, BusinessService> {


    private GoogleGeocodingService geocodingService;

    protected BusinessService(BusinessRepository childRepository, GoogleGeocodingService geocodingService) {
        super(childRepository);
        this.geocodingService = geocodingService;
    }

    public Long findIdByBusinessName(String businessName) {
        return repository.findIdByBusinessName(businessName);
    }

    public boolean existsByBusinessName(String businessName) {
        return repository.existsByBusinessName(businessName);
    }

    @Override
    protected void verifySpecificAttributes(Business entity) {

        if (entity.getBusinessName() == null || entity.getBusinessName().trim().isEmpty()) {
            throw new InvalidEntityException("El nombre del negocio no puede estar vacío.");
        }
        if (repository.existsByBusinessName(entity.getBusinessName())) {
            throw new InvalidEntityException("Ya existe un negocio con ese nombre.");
        }

        if (entity.getTax() <= 0) {
            throw new IllegalArgumentException("El CUIT debe ser un número válido.");
        }
        if (repository.existsByTax(entity.getTax())) {
            throw new IllegalArgumentException("Ya existe un negocio con ese CUIT.");
        }

        if (entity.getSlogan() == null || entity.getSlogan().trim().isEmpty()) {
            throw new IllegalArgumentException("El slogan no puede estar vacío.");
        }
        if (entity.getDescription() == null || entity.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }

    }

    public Business findByBusinessName(String name){
        return repository.findByBusinessName(name);
    }


    public void changeAddress(Long id, String address) {
        if(address== null || address.isBlank()) {
            throw new IllegalArgumentException("la direccion no puede estar vacia");
        }
        boolean direccionValida = geocodingService.validateAddress(address);
        if (!direccionValida) {
            throw new IllegalArgumentException("la direccion no es valida segun Google Maps");
        }
        updateField(id, "address",  address);
    }


    public boolean addresValidation(String address) throws RuntimeException{
        return geocodingService.validateAddress(address);
    }


}
