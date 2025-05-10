package Caprish.Service.interfaces.users;

import java.util.List;
import java.util.Optional;
import Caprish.Model.imp.users.User;

public interface IUserBasicGenericService<T extends User> {
    T save(T entity);
    Optional<T> findById(Long id);
    List<T> findAll();
    void deleteById(Long id);
    Optional<T> findByEmail(String email);
    void changePassword(Long id, String newPassword);
    void updateEmail(Long id, String newEmail);
}
