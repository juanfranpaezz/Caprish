package Caprish.config;

import Caprish.Model.enums.CartStatus;
import Caprish.Model.enums.CartType;
import Caprish.Model.enums.SenderType;
import Caprish.Model.enums.Role;
import Caprish.Repository.enums.CartStatusRepository;
import Caprish.Repository.enums.CartTypeRepository;
import Caprish.Repository.enums.RoleRepository;
import Caprish.Repository.enums.SenderTypeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired private RoleRepository roleRepository;
    @Autowired private CartStatusRepository cartStatusRepository;
    @Autowired private CartTypeRepository cartTypeRepository;
    @Autowired private SenderTypeRepository senderTypeRepository;
    @PostConstruct
    public void init() {
//         Roles
        createIfNotExists(roleRepository, Role::new, "ROLE_USER");
        createIfNotExists(roleRepository, Role::new, "ROLE_BOSS");
        createIfNotExists(roleRepository, Role::new, "ROLE_CLIENT");
        createIfNotExists(roleRepository, Role::new, "ROLE_SUPERVISOR");
        createIfNotExists(roleRepository, Role::new, "ROLE_EMPLOYEE");

        // CartStatus
        createIfNotExists(cartStatusRepository, CartStatus::new, "OPEN");
        createIfNotExists(cartStatusRepository, CartStatus::new, "CONFIRMED");

        // CartType
        createIfNotExists(cartTypeRepository, CartType::new, "PURCHASE");
        createIfNotExists(cartTypeRepository, CartType::new, "SALE");

        // SenderType
        createIfNotExists(senderTypeRepository, SenderType::new, "CLIENT");
        createIfNotExists(senderTypeRepository, SenderType::new, "STAFF");
        // Roles
        roleRepository.save(new Role("ROLE_USER"));
        roleRepository.save(new Role("ROLE_BOSS"));
        roleRepository.save(new Role("ROLE_CLIENT"));
        roleRepository.save(new Role("ROLE_SUPERVISOR"));
        roleRepository.save(new Role("ROLE_EMPLOYEE"));

        // Cart Status
        cartStatusRepository.save(new CartStatus("OPEN"));
        cartStatusRepository.save(new CartStatus("CONFIRMED"));

        // Cart Type
        cartTypeRepository.save(new CartType("PURCHASE"));
        cartTypeRepository.save(new CartType("SALE"));

        // Sender Type
        senderTypeRepository.save(new SenderType("CLIENT"));
        senderTypeRepository.save(new SenderType("STAFF"));

    }



    /**
     * Genérico para crear una entidad “enum-like” si no existe.
     *
     * @param repo   el JpaRepository de la entidad
     * @param ctor   referencia al constructor que recibe un String (el id)
     * @param id     el valor de la PK a insertar
     * @param <E>    tipo de entidad
     * @param <R>    tipo de repositorio
     */
    private <E, R extends CrudRepository<E, String>>
    void createIfNotExists(R repo, java.util.function.Function<String, E> ctor, String id) {
        if (!repo.existsById(id)) {
            E entity = ctor.apply(id);
            repo.save(entity);
        }
    }
}
