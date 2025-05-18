package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;


@Service
public class BranchService extends MyObjectGenericService<Branch, BranchRepository> {
    protected BranchService(BranchRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<Branch> getEntityClass() {
        return Branch.class;
    }


    public void changeAddress(Long id, String address) {
        ((BranchService) AopContext.currentProxy()).updateField(id, "address", "address");
    }

    public void changeBranch_type(Long id, String branch_type) {
        ((BranchService) AopContext.currentProxy()).updateField(id, "branch_type", branch_type);
    }
}