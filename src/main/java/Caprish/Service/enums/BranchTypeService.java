package Caprish.Service.enums;

import Caprish.Model.enums.BranchType;
import Caprish.Repository.enums.BranchTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BranchTypeService extends MyEnumGenericService<BranchType, BranchTypeRepository, BranchTypeService> {
    protected BranchTypeService(BranchTypeRepository childRepository) {
        super(childRepository);
    }

}
