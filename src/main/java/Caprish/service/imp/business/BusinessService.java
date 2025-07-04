package Caprish.Service.imp.business;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.MyObjectGenericService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import Caprish.Service.others.GoogleGeocodingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import Caprish.Model.imp.users.Staff;

@Slf4j
@Service
public class BusinessService extends MyObjectGenericService<Business, BusinessRepository, BusinessService> {


    @Autowired
    private CredentialService credentialService;
    @Autowired
    private StaffService staffService;
    private GoogleGeocodingService geocodingService;

    protected BusinessService(BusinessRepository childRepository, GoogleGeocodingService geocodingService) {
        super(childRepository);
        this.geocodingService = geocodingService;
    }

    public Long findIdByBusinessName(String businessName) {
        return repository.findIdByBusinessName(businessName);
    }

    public Long resolveBusinessId(UserDetails userDetails) {
        Long credId = credentialService.getIdByUsername(userDetails.getUsername());
        return staffService.findByCredentialId(credId)
                .map(Staff::getBusiness)
                .map(b -> b.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "No autorizado: no se encontró negocio asociado."));
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


    public boolean isActiveById(Long id){
        return repository.getActiveById(id);
    }

    public boolean addresValidation(String address) throws RuntimeException{
        return geocodingService.validateAddress(address);
    }


    public boolean existsByTax(Long tax){
        return repository.existsByTax(tax);
    }



}
