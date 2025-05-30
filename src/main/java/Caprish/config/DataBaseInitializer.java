package Caprish.config;


import Caprish.Model.imp.users.PlatformAdmin;
import Caprish.Model.imp.users.Role;
import Caprish.Repository.interfaces.users.PlatformAdminRepository;
import Caprish.Repository.interfaces.users.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner seedAdmin(PlatformAdminRepository adminRepo,
                                       RoleRepository roleRepo,
                                       PasswordEncoder encoder) {
        return args -> {
            // 1) Preload roles
            String[] roleNames = {
                    "ROLE_ADMIN", "ROLE_BOSS", "ROLE_SUPERVISOR",
                    "ROLE_EMPLOYEE", "ROLE_CLIENT", "ROLE_USER"
            };
            for (String rn : roleNames) {
                roleRepo.findByName(rn)
                        .orElseGet(() -> roleRepo.save(new Role(rn)));
            }

            String email = "admin@caprish.com";
            if (!adminRepo.existsByEmail(email)) {
                // Asegurate de que exista el role ADMIN
                Role adminRole = roleRepo.findByName("ROLE_ADMIN")
                        .orElseGet(() -> roleRepo.save(new Role("ROLE_ADMIN")));

                PlatformAdmin a = new PlatformAdmin();
                a.setEmail(email);
                a.setPassword_hash(encoder.encode("Secret123!"));
                a.setRole(adminRole);
                a.setFirst_name("Fran");
                a.setLast_name("Paez");
                adminRepo.save(a);
                System.out.println("Admin seed creado: " + email);
            }
        };
    }
}