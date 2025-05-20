package Caprish.Service.imp.users;

import Caprish.Exception.UserException;
import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.UserGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;

import java.util.Optional;

public abstract class UserGenericService<M extends User, R extends UserGenericRepository<M>, S extends UserGenericService<M,R,S>> extends MyObjectGenericService<M, R> {


    protected UserGenericService(R childRepository) {
        super(childRepository);
    }

    protected void validateBeforeSave(M entity) {
    }

    protected void postSave(M original, M persisted) {
    }


    public Optional<M> findByEmail(String email) {
        return repository.findByEmail(email);
    }


    public void register(M user) throws UserException {
        if (Optional.ofNullable(user).isEmpty()) {
            throw new UserException("El user no puede ser nulo");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new UserException("El email no puede ser nulo");
        } else repository.save(user);
    }

    public boolean log(M user) throws UserException {
        if (Optional.ofNullable(user).isEmpty()) {
            throw new UserException("El user no puede ser nulo");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new UserException("El email no puede ser nulo");
        }
        Optional<M> userOp = repository.findByEmail(user.getEmail());
        if (userOp.isPresent()) {
            if (userOp.get().equals(user)) {
                return true;
            } else throw new UserException("La contrasenia es incorrecta");
        } else throw new UserException("El usuario no existe");
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
        if (password.length() < 8 || password.length() > 15) {

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

    @SuppressWarnings("unchecked")
    public void changePassword_hash(Long id, String password) {
        ((S) AopContext.currentProxy()).updateField(id, "password_hash", password);
    }

    @SuppressWarnings("unchecked")
    public void changeEmail(Long id, String email) {
        ((S) AopContext.currentProxy()).updateField(id, "email", email);
    }

    @SuppressWarnings("unchecked")
    public void changeFirst_name(Long id, String first_name) {
        ((S) AopContext.currentProxy()).updateField(id, "first_name", first_name);
    }

    @SuppressWarnings("unchecked")
    public void changeLast_name(Long id, String last_name) {
        ((S) AopContext.currentProxy()).updateField(id, "last_name", last_name);
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
