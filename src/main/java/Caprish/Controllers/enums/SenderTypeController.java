package Caprish.Controllers.enums;

import Caprish.Model.enums.SenderType;
import Caprish.Repository.enums.SenderTypeRepository;
import Caprish.Service.enums.SenderTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sender_type")
public class SenderTypeController extends MyEnumGenericController<SenderType, SenderTypeRepository, SenderTypeService> {
    public SenderTypeController(SenderTypeService childService) {
        super(childService);
    }
}
