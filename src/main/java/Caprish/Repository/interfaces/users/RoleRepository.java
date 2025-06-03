package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.Role;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.Optional;

public interface RoleRepository extends MyObjectGenericRepository<Role> {
    Optional<Role> findByName(String admin);
}
