package Caprish.Service.enums;

import Caprish.Model.enums.CartStatus;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Repository.enums.CartStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartStatusService extends MyEnumGenericService<CartStatus, CartStatusRepository, CartStatusService> {
    protected CartStatusService(CartStatusRepository childRepository) {
        super(childRepository);
    }

}
