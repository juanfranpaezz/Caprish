package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
@Validated
public class ClientController extends MyObjectGenericController<Client, ClientRepository, ClientService> {

    @Autowired
    CredentialService credentialService;

    public ClientController(ClientService childService) {
        super(childService);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createClient(@Valid @RequestBody Client entity, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (entity == null || !userDetails.getAuthorities().toString().equals("ROLE_CLIENT")) return ResponseEntity.badRequest().build();
            entity.setId(credentialService.getIdByUsername(userDetails.getUsername()));
            return ResponseEntity.ok(String.valueOf(service.save(entity)));
        } catch (InvalidEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }

    @PutMapping("/updatePhone")
    public ResponseEntity<String> updatePhone(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody Map<String,String> payload) {
        return update(service.getIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())), "phone", payload.get("phone"));
    }

    @PutMapping("/updateTax")
    public ResponseEntity<String> updateTax(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody Map<String,String> payload) {
        return update(service.getIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())), "tax", payload.get("tax"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Client>> findAllObjects() {
        return findAll();
    }



}
