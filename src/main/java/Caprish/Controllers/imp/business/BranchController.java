package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectController;
import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.business.BranchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branch")
public class BranchController extends MyObjectController<Branch, BranchRepository, BranchService> {

    public BranchController(BranchService service) {
        super(service);
    }

}