package Caprish.Service.imp.users;

import Caprish.Exception.UserException;
import Caprish.Model.BeanUtils;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.UserGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;

import java.util.Optional;

@Slf4j
public abstract class UserGenericService<M extends User, R extends UserGenericRepository<M>, S extends UserGenericService<M,R,S>> extends MyObjectGenericService<M, R, S> {


    protected UserGenericService(R childRepository) {
        super(childRepository);
    }

    @Override
    protected void verifySpecificAttributes(M entity) {

    }

    public void register(M user) throws UserException {
        if (Optional.ofNullable(user).isEmpty()) {
            throw new UserException("El user no puede ser nulo");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {//MAIL
            throw new UserException("El email no puede ser nulo");
        }
        verifyPassword(user.getPassword_hash());
        save(user);
    }

    public void log(M user) throws UserException {
        Optional<M> userOp = repository.findByEmail(user.getEmail());
        if (userOp.isEmpty()) { throw new UserException("El usuario no existe");
        }
        if(!user.getPassword_hash().equals(userOp.get().getPassword_hash())){
            throw new UserException("La contraseña no es correcta");
        }
    }


    public void deleteMyOwn(M user) throws UserException {
        Optional<M> userOp = repository.findByEmail(user.getEmail());
        if (userOp.isPresent()) {
            if (userOp.get().equals(user)) repository.deleteById(user.getId());
            else throw new UserException("La contrasenia es incorrecta");
        } else throw new UserException("El usuario no existe");
    }

    private void delete(M user) throws UserException {
        if (Optional.ofNullable(user).isEmpty()) throw new UserException("El user no puede ser nulo");
        else if (user.getEmail() == null || user.getEmail().trim().isEmpty())
            throw new UserException("El email no puede ser nulo");
        else if (repository.findByEmail(user.getEmail()).isEmpty()) throw new UserException("El usuario no existe");
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



    /*    public final T save(T entity) {
        validateBeforeSave(entity);
        T saved = userRepository.save(entity);
        postSave(entity, saved);
        return saved;
    }

    public Optional<T> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<T> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
*/
}
