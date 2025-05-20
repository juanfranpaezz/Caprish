package Caprish.Repository.enums;

import Caprish.Model.enums.MyEnum;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.Optional;

public interface MyEnumGenericRepository<M extends MyEnum> extends MyObjectGenericRepository<M> {
    Optional<M> findByValue(String email);
}
