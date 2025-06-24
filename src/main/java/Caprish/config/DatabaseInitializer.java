package Caprish.config;

import Caprish.Model.enums.CartStatus;
import Caprish.Model.enums.CartType;
import Caprish.Model.enums.SenderType;
import Caprish.Model.enums.Role;
import Caprish.Model.imp.business.Address;
import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.business.Product;
import Caprish.Model.imp.messaging.Chat;
import Caprish.Model.imp.messaging.Message;
import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.Item;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.enums.CartStatusRepository;
import Caprish.Repository.enums.CartTypeRepository;
import Caprish.Repository.enums.RoleRepository;
import Caprish.Repository.enums.SenderTypeRepository;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Repository.interfaces.messaging.ChatRepository;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Repository.interfaces.sales.ItemRepository;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Repository.interfaces.users.CredentialRepository;
import Caprish.Repository.interfaces.users.StaffRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DatabaseInitializer {

    @Autowired private CredentialRepository credentialRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private CartStatusRepository cartStatusRepository;
    @Autowired private CartTypeRepository cartTypeRepository;
    @Autowired private SenderTypeRepository senderTypeRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private BusinessRepository businessRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private StaffRepository staffRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private ChatRepository chatRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private MessageRepository messageRepository;
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

//        // Business
//        Business b1 = new Business("TechZone", new Address("Av. Siempre Viva 742","mar del plata","buenos aires","argentina"),"alta empresa","para unir al mundo", 123L,true,null,null,null);
//        Business b2 = new Business("GamerHouse", new Address("Av. Siempre Viva 742","mar del plata","buenos aires","argentina"),"alta empresa bro","para ser feliz", 123L,true,null,null,null);
//        businessRepository.save(b1);
//        businessRepository.save(b2);
//
//        // Credentials
//        Credential boss = new Credential("admin@example.com", passwordEncoder.encode("1234"), "Admin", "Boss", true, roleRepository.findById("ROLE_BOSS").get(), 1L);
//        Credential staff = new Credential ("staff1@example.com", passwordEncoder.encode("1234"), "Juan", "Pérez", true, roleRepository.findById("ROLE_EMPLOYEE").get(), 2L);
//        Credential client = new Credential("client1@example.com", passwordEncoder.encode("1234"), "Lucía", "Gómez", true, roleRepository.findById("ROLE_CLIENT").get(), 1L);
//        credentialRepository.save(boss);
//        credentialRepository.save(staff);
//        credentialRepository.save(client);
//
//        // Staff
//        staffRepository.save(new Staff(staff, b1, null));
//
//        // Client
//        Client clientito=new Client( client, 123,"la concha de mi madre",null,null);
//        clientRepository.save(clientito);
//
//
//        // Cart
//        Cart cart = new Cart(new CartType("PURCHASE"), clientito,
//                new CartStatus("OPEN"), null,null,LocalDate.now());
//        cartRepository.save(cart);
//
//        // Products
//        Product p1 = new Product(b1,"Auriculares Bluetooth","tremendo",new BigDecimal("9999.99"), null,2);
//        Product p2 = new Product(b2,"Teclado Mecánico", "util",new BigDecimal("19.99"), null,5);
//        productRepository.save(p1);
//        productRepository.save(p2);
//
//        // Item
//        itemRepository.save(new Item(cart, p1, 3));
//
//        // Chat y Message
//        Chat chat = new Chat(b1, clientito,null);
//        chatRepository.save(chat);
//        messageRepository.save(new Message( chat, new SenderType("AAA"), "hola", LocalDateTime.now()));
//        messageRepository.save(new Message(chat,new SenderType("a"), "chat", LocalDateTime.now()));
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
