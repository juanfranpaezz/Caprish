package Caprish.Service.imp.business;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.business.StockRepository;
import Caprish.Service.imp.MyObjectGenericService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockService extends MyObjectGenericService<Stock, StockRepository, StockService> {
    protected StockService(StockRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected void verifySpecificAttributes(Stock entity) {

        if (entity.getProduct() == null || entity.getProduct().getId() == null) {
            throw new InvalidEntityException("El producto no puede ser nulo o no tener ID.");
        }

        if (entity.getBranch() == null || entity.getBranch().getId() == null) {
            throw new InvalidEntityException("La sucursal no puede ser nula o no tener ID.");
        }

        if (repository.existsByProductIdAndBranchId(entity.getProduct().getId(), entity.getBranch().getId())) {
            throw new InvalidEntityException("Ya existe un stock para ese producto y esa sucursal.");
        }

    }

    public int findStock(String product, String business) throws EntityNotFoundException {
        return repository.findStockByProductAndBusiness(product, business);
    }
}