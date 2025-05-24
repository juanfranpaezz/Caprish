package Caprish.Service.enums;

import Caprish.Model.enums.BranchType;
import Caprish.Repository.enums.BranchTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class BranchTypeService extends MyEnumGenericService<BranchType, BranchTypeRepository, BranchTypeService> {
    protected BranchTypeService(BranchTypeRepository childRepository) {
        super(childRepository);
    }

}
