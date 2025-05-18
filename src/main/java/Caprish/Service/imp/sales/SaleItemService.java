package Caprish.Service.imp.sales;

import Caprish.Model.imp.sales.SaleItem;
import Caprish.Repository.interfaces.sales.SaleItemRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;


@Service
public class SaleItemService extends MyObjectGenericService<SaleItem, SaleItemRepository> {
    protected SaleItemService(SaleItemRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<SaleItem> getEntityClass() {
        return SaleItem.class;
    }
}