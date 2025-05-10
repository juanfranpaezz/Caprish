package Caprish.Service.imp.users;

import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.UserBasicGenericRepository;
import Caprish.Service.interfaces.users.IUserBasicGenericService;

import java.util.List;
import java.util.Optional;

public abstract class UserBasicGenericService<T extends User>
        implements IUserBasicGenericService<T> {

    protected final UserBasicGenericRepository<T, Long> repository;

    protected UserBasicGenericService(UserBasicGenericRepository<T, Long> repository) {
        this.repository = repository;
    }

    public final T save(T entity) {
        validateBeforeSave(entity);
        T saved = repository.save(entity);
        postSave(entity, saved);
        return saved;
    }

    @Override
    public void updateEmail(Long id, String newEmail) {
        T user = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        user.setEmail(newEmail);
        repository.save(user);
    }

    @Override
    public void changePassword(Long id, String newPassword) {
        T user = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        user.setPasswordHash(newPassword);
        repository.save(user);
    }

    protected void validateBeforeSave(T entity) {}
    protected void postSave(T original, T persisted) {}

    @Override public Optional<T> findById(Long id) { return repository.findById(id); }
    @Override public List<T> findAll()       { return repository.findAll(); }
    @Override public void deleteById(Long id){ repository.deleteById(id); }
    @Override public Optional<T> findByEmail(String email){
        return Optional.ofNullable(repository.findByEmail(email));
    }
}
