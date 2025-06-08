package Caprish.Service.imp.sales;

import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.sales.Item;
import Caprish.Repository.interfaces.sales.ItemRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ItemService extends MyObjectGenericService<Item, ItemRepository, ItemService> {
    protected ItemService(ItemRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected void verifySpecificAttributes(Item entity) {
        if (entity.getProduct() == null) {
            throw new IllegalArgumentException("El producto del ítem no puede ser nulo");
        }

        if (entity.getCart() == null) {
            throw new IllegalArgumentException("El carrito asociado al ítem no puede ser nulo");
        }

        if (entity.getQuantity() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
    }
}
