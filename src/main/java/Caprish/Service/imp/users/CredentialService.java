package Caprish.Service.imp.users;


import Caprish.Exception.UserException;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.LoginRequest;
import Caprish.Model.imp.users.LoginResponse;
import Caprish.Repository.interfaces.users.CredentialRepository;
import Caprish.Service.imp.MyObjectGenericService;
import Caprish.Service.others.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CredentialService extends MyObjectGenericService<Credential, CredentialRepository, CredentialService> {
    @Autowired
     private UserDetailsService userDetailsService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtService jwtService;

    protected CredentialService(CredentialRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected void verifySpecificAttributes(Credential entity) {

    }

    public void verifyPassword(String password) throws UserException {
        if (password == null) throw new UserException("La contrasenia no puede ser nula");
        if (password.length() < 8 || password.length() > 20) {
            throw new UserException("La contraseña debe tener minimo 8 caracteres y maximo 20");
        }
        String lowerCase = ".*[a-z].*";
        String upperCase = ".*[A-Z].*";
        String number = ".*\\d.*";
        if (!password.matches(lowerCase)) {
            throw new UserException("La contrasenia tiene que tener minimo una minuscula");
        }
        if (!password.matches(upperCase)) {
            throw new UserException("La contrasenia tiene que tener minimo una mayuscula");
        }
        if (!password.matches(number)) {
            throw new UserException("La contrasenia tiene que tener minimo un numero");
        }
    }

    public LoginResponse doLogin(String username) throws UserException {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }

    public Long getIdByUserDetails(UserDetails userDetails){
        return repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")).getId();
    }

    public Optional<Credential> findByUsername(String username) {

        return repository.findByUsername(username);
    }

}
