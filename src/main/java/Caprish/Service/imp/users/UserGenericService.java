package Caprish.Service.imp.users;

import Caprish.Exception.UserException;
import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.UserGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;

import java.util.Optional;

public abstract class UserGenericService<T extends User, R extends UserGenericRepository<T, Long>> extends MyObjectGenericService<T, R> {


    protected UserGenericService(R childRepository) {
        super(childRepository);
    }

    public void changePassword(Long id, String newPassword) {
        T user = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        user.setPassword_hash(newPassword);
        repository.save(user);
    }

    protected void validateBeforeSave(T entity) {
    }

    protected void postSave(T original, T persisted) {
    }


    public Optional<T> findByEmail(String email) {
        return repository.findByEmail(email);
    }


    public void register(T user) throws UserException {
        if (Optional.ofNullable(user).isEmpty()) {
            throw new UserException("El user no puede ser nulo");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new UserException("El email no puede ser nulo");
        } else repository.save(user);
    }

    public boolean log(T user) throws UserException {
        if (Optional.ofNullable(user).isEmpty()) {
            throw new UserException("El user no puede ser nulo");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new UserException("El email no puede ser nulo");
        }
        Optional<T> userOp = repository.findByEmail(user.getEmail());
        if (userOp.isPresent()) {
            if (userOp.get().equals(user)) {
                return true;
            } else throw new UserException("La contrasenia es incorrecta");
        } else throw new UserException("El usuario no existe");
    }


    public void deleteMyOwn(T user) throws UserException {
        Optional<T> userOp = repository.findByEmail(user.getEmail());
        if (userOp.isPresent()) {
            if (userOp.get().equals(user)) repository.deleteById(user.getId());
            else throw new UserException("La contrasenia es incorrecta");
        } else throw new UserException("El usuario no existe");
    }

    private void delete(T user) throws UserException {
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


    public int changePassword_hash(Long id, String password) {
        return updateField(id, "password", password);
    }


    public int changeEmail(Long id, String email) {
        return updateField(id, "email", email);
    }


    public int changeFirst_name(Long id, String first_name) {
        return updateField(id, "first_name", first_name);
    }


    public int changeLast_name(Long id, String last_name) {
        return updateField(id, "last_name", last_name);
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
