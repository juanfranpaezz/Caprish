package Caprish.config;

import Caprish.Model.imp.users.PlatformAdmin;
import Caprish.Model.imp.users.Role;
import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.PlatformAdminRepository;
import Caprish.Repository.interfaces.users.RoleRepository;
import Caprish.Repository.interfaces.users.UserGenericRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseInitializerTest {

    @Bean
    public CommandLineRunner seedAdmin(UserGenericRepository userGenericRepository,
                                       PlatformAdminRepository adminRepository,
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
            if (!userGenericRepository.existsByUsername(email)) {
                // Asegurate de que exista el role ADMIN
                Role adminRole = roleRepo.findByName("ROLE_ADMIN")
                        .orElseGet(() -> roleRepo.save(new Role("ROLE_ADMIN")));

                User a = new User();
                a.setUsername(email);
                a.setPassword_hash(encoder.encode("Secret123!"));
                a.setRole(adminRole);
                a.setEnabled(true);
                a.setFirst_name("Fran");
                a.setLast_name("Paez");
                adminRepository.save(new PlatformAdmin(userGenericRepository.save(a)));
                System.out.println("Admin seed creado: " + email);
            }
        };
    }
}