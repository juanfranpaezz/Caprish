package Caprish.config;
import Caprish.Model.enums.Role;
import Caprish.Model.imp.users.Credential;
import Caprish.Repository.enums.RoleRepository;
import Caprish.Repository.interfaces.users.CredentialRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializerTest {
    @Autowired private CredentialRepository credentialRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;



    @PostConstruct
    public void init() {
        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_BOSS");
        createRoleIfNotExists("ROLE_CLIENT");
        createRoleIfNotExists("ROLE_SUPERVISOR");
        createRoleIfNotExists("ROLE_EMPLOYEE");


        // Ahora podés crear usuarios sin error
        Role adminRole = roleRepository.findById("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role ADMIN not found in DB"));

        Credential admin = new Credential();
        admin.setFirst_name("aa");
        admin.setLast_name("bb");
        admin.setEnabled(true);
        admin.setUsername("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("1234"));
        admin.setRole(adminRole);

        credentialRepository.save(admin);
    }

    private void createRoleIfNotExists(String id) {
        if (!roleRepository.existsById(id)) {
            Role role = new Role();
            role.setId(id);
            roleRepository.save(role);
        }
    }

}
