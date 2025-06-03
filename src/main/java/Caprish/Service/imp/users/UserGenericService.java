package Caprish.Service.imp.users;

import Caprish.Exception.UserException;
import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.UserGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class UserGenericService extends MyObjectGenericService<User, UserGenericRepository, UserGenericService> {


    protected UserGenericService(UserGenericRepository childRepository) {
        super(childRepository);
    }

    public void register(User user) throws UserException {
        if (Optional.ofNullable(user).isEmpty()) {
            throw new UserException("El user no puede ser nulo");
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {//MAIL
            throw new UserException("El email no puede ser nulo");
        }
        verifyPassword(user.getPassword_hash());
        save(user);
    }

    public void log(User user) throws UserException {
        Optional<User> userOp = repository.findByUsername(user.getUsername());
        if (userOp.isEmpty()) { throw new UserException("El usuario no existe");
        }
        if(!user.getPassword_hash().equals(userOp.get().getPassword_hash())){
            throw new UserException("La contraseña no es correcta");
        }
    }


    public void deleteMyOwn(User user) throws UserException {
        Optional<User> userOp = repository.findByUsername(user.getUsername());
        if (userOp.isPresent()) {
            if (userOp.get().equals(user)) repository.deleteById(user.getId());
            else throw new UserException("La contrasenia es incorrecta");
        } else throw new UserException("El usuario no existe");
    }

    private void delete(User user) throws UserException {
        if (Optional.ofNullable(user).isEmpty()) throw new UserException("El user no puede ser nulo");
        else if (user.getUsername() == null || user.getUsername().trim().isEmpty())
            throw new UserException("El email no puede ser nulo");
        else if (repository.findByUsername(user.getUsername()).isEmpty()) throw new UserException("El usuario no existe");
        else repository.deleteById(user.getId());
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

    @Override
    protected void verifySpecificAttributes(User entity) {

    }
}
