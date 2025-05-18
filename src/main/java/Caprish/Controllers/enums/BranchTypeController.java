package Caprish.Controllers.enums;

import Caprish.Model.enums.BranchType;
import Caprish.Repository.enums.BranchTypeRepository;
import Caprish.Service.enums.BranchTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branch_type")
public class BranchTypeController extends MyEnumGenericController<BranchType, BranchTypeRepository, BranchTypeService> {
    public BranchTypeController(BranchTypeService childService) {
        super(childService);
    }
}
