package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;


@Service
public class BranchService extends MyObjectGenericService<Branch> {
    protected BranchService(BranchRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<Branch> getEntityClass() {
        return Branch.class;
    }


    public int changeAddress(Long id, String address) {return updateField(id, "address", address);}


    public int changeBranch_type(Long id, String branch_type) {return updateField(id, "branch_type", branch_type);}
}