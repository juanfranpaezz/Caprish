package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.Optional;

public interface UserGenericRepository extends MyObjectGenericRepository<User> {
    Optional<User> findByUsername(String email);
    boolean existsByUsername(String email);
}
