package Caprish.Service.imp.users;

import Caprish.Exception.UserException;
import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.UserBasicGenericRepository;

import java.util.List;
import java.util.Optional;

public abstract class UserBasicGenericService<T extends User> {

    protected final UserBasicGenericRepository<T, Long> repository;

    protected UserBasicGenericService(UserBasicGenericRepository<T, Long> repository) {
        this.repository = repository;
    }

    public final T signUp(T entity) throws UserException {
        validateBeforeSave(entity);
        T saved = repository.save(entity);
        postSave(entity, saved);
        return saved;
    }

    public void changePassword(Long id, String newPassword) throws UserException {
        T user = repository.findById(id)
                .orElseThrow(() -> new UserException("Usuario no encontrado: " + id));
        verifyPassword(newPassword);
        user.setPassword_hash(newPassword);
        repository.save(user);
    }

    protected void validateBeforeSave(T entity) throws UserException {
        if(entity.getPassword_hash() == null || entity.getPassword_hash().isBlank()) {
            throw new UserException("La contraseña no puede estar vacía");
        }
        if(entity.getEmail() == null || entity.getEmail().isBlank()) {
            throw new UserException("El mail no puede estar vacío");
        }
        if(entity.getFirst_name() == null || entity.getFirst_name().isBlank()) {
            throw new UserException("El nombre no puede ser nulo ni vacío");
        }
        if(entity.getLast_name() == null || entity.getLast_name().isBlank()) {
            throw new UserException("El apellido no puede ser nulo ni vacío");
        }
        verifyPassword(entity.getPassword_hash());
    }
    protected void postSave(T original, T persisted) {
    }

    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
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
        if (user.getPassword_hash()==null) {
            throw new UserException("La contraseña no puede ser nula");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new UserException("El email no puede ser nulo");
        }
        Optional<T> userOp = repository.findByEmail(user.getEmail());
        if (userOp.isPresent()) {
            if (userOp.get().equals(user)) {
                return true;
            } else throw new UserException("La contraseña es incorrecta");
        } else throw new UserException("El usuario no existe");
    }


    public void deleteMyOwn(T user) throws UserException {
        Optional<T> userOp = repository.findByEmail(user.getEmail());
        if (userOp.isPresent()) {
            if (userOp.get().equals(user)) repository.deleteById(user.getId());
            else throw new UserException("La contrasenia es incorrecta");
        } else throw new UserException("El usuario no existe");
    }

    private void deleteUser(T user) throws UserException {
        if (Optional.ofNullable(user).isEmpty()) throw new UserException("El user no puede ser nulo");
        else if (user.getEmail() == null || user.getEmail().trim().isEmpty()) throw new UserException("El email no puede ser nulo");
        else if (repository.findByEmail(user.getEmail()).isEmpty()) throw new UserException("El usuario no existe");
        else repository.deleteById(user.getId());
    }

    public void verifyPassword(String password) throws UserException {
        if (password == null) throw new UserException("La contrasenia no puede ser nula");
        if (password.length() < 8 || password.length() > 15) {
            throw new UserException("La contraseña debe tener al menos  8 caracteres y maximo 15 caracteres");
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
}
