package Caprish.Service.enums;

import Caprish.Model.enums.SenderType;
import Caprish.Repository.enums.SenderTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class SenderTypeService extends MyEnumGenericService<SenderType, SenderTypeRepository, SenderTypeService> {
    protected SenderTypeService(SenderTypeRepository childRepository) {
        super(childRepository);
    }
}
