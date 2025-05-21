package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;


@Service
public class BranchService extends MyObjectGenericService<Branch, BranchRepository, BranchService> {
    protected BranchService(BranchRepository childRepository) {
        super(childRepository);
    }


}