package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.users.Credential;
import Caprish.Repository.interfaces.users.CredentialRepository;
import Caprish.Service.imp.users.CredentialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/credential")
public class CredentialController extends MyObjectGenericController<Credential, CredentialRepository, CredentialService> {

    public CredentialController(CredentialService service) {
        super(service);
    }

    @Override
    public ResponseEntity<String> createObject(Credential entity) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteObject(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Credential> findObjectById(Long id) {
        return null;
    }

    @Override
    public List<Credential> findAllObjects() {
        return List.of();
    }
}
