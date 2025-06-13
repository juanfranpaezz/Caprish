package Caprish.Service.enums;

import Caprish.Model.enums.CartType;
import Caprish.Repository.enums.CartTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartTypeService extends MyEnumGenericService<CartType, CartTypeRepository, CartTypeService> {
    public CartTypeService(CartTypeRepository childRepository) {
        super(childRepository);
    }

}
