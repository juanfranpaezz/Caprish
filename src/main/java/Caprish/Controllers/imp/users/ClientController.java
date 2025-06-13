package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.sales.CartService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
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
import java.util.Optional;

import Caprish.Model.imp.users.Credential;


@RestController
@RequestMapping("/client")
@Validated
public class ClientController extends MyObjectGenericController<Client, ClientRepository, ClientService> {

    @Autowired
    CredentialService credentialService;
    @Autowired
    StaffService staffService;
    @Autowired
    CartService cartService;

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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteObject(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Credential> c = credentialService.findById(credentialService.getIdByUsername(userDetails.getUsername()));
        if(c.isEmpty()) return ResponseEntity.badRequest().build();
        update(c.get().getId(), "enabled", false);
        return ResponseEntity.ok("Borrado exitosamente");
    }

    @PutMapping("/update-phone")
    public ResponseEntity<String> updatePhone(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody Map<String,String> payload) {
        return update(service.getIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())), "phone", payload.get("phone"));
    }

    @PutMapping("/update-tax")
    public ResponseEntity<String> updateTax(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody Map<String,String> payload) {
        return update(service.getIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())), "tax", payload.get("tax"));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Client> findObjectById(@Positive @PathVariable String username, @AuthenticationPrincipal UserDetails userDetails) {
        Long clientId = service.getIdByCredentialId(credentialService.getIdByUsername(username));
        if (!cartService.existsByBusinessIdAndClientId(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())), clientId)) return ResponseEntity.badRequest().build();
        return findById(clientId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> findAllObjects(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(cartService.findClientsByBusinessId(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()))));
    }

}
