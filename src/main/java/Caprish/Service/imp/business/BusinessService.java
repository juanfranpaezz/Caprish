package Caprish.Service.imp.business;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BusinessService extends MyObjectGenericService<Business, BusinessRepository, BusinessService> {

    protected BusinessService(BusinessRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected void verifySpecificAttributes(Business entity) {

        if (entity.getBussiness_name() == null || entity.getBussiness_name().trim().isEmpty()) {
            throw new InvalidEntityException("El nombre del negocio no puede estar vacío.");
        }
        if (repository.existsByBussinessName(entity.getBussiness_name())) {
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


}
