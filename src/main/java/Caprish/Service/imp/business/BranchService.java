package Caprish.Service.imp.business;

import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BranchService extends MyObjectGenericService<Branch, BranchRepository, BranchService> {
    protected BranchService(BranchRepository childRepository) {
        super(childRepository);
    }


    @Override
    protected void verifySpecificAttributes(Branch entity) {

    }
}