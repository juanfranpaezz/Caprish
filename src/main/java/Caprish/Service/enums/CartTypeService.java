package Caprish.Service.enums;

import Caprish.Model.enums.CartType;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Repository.enums.CartTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class CartTypeService extends MyEnumGenericService<CartType, CartTypeRepository, CartTypeService> {
    public CartTypeService(CartTypeRepository childRepository) {
        super(childRepository);
    }

}
