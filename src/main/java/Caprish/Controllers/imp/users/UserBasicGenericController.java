package Caprish.Controllers.imp.users;

import Caprish.Exception.UserException;
import Caprish.Model.imp.users.User;
import Caprish.Service.imp.users.UserBasicGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/usuarios")
public abstract class UserBasicGenericController<T extends User> {

    protected final UserBasicGenericService<T> service;

    protected UserBasicGenericController(UserBasicGenericService<T> service) {
        this.service = service;
    }

    @PostMapping("/eliminar")
    public ResponseEntity <?> deleteUser(@RequestBody T user) {
        try {
            service.deleteMyOwn(user);
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity <?> logUser(@RequestBody T user) throws UserException {
        service.log(user);
        return ResponseEntity.ok("Usuario logueado exitosamente");
    }

    @PostMapping("/password-change")
    public ResponseEntity <?> changePassword(@RequestBody String newPassword) throws UserException {
        //autenticacion con spring security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        T usercito = service.findByEmail(email)
                .orElseThrow(() -> new UserException("Usuario no encontrado"));
        service.changePassword(usercito.getId(), newPassword);
        return ResponseEntity.ok("Contraseña cambiada exitosamente");
    }
}
