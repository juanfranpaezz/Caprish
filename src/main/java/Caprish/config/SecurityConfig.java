package Caprish.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // rutas públicas, ni siquiera necesitan autenticarse
                        .requestMatchers("/api/publico").permitAll()

                        // cualquier usuario autenticado con ROLE_USER o superior
                        .requestMatchers("/api/user/**").hasRole("USER")

                        // rutas exclusivas de CLIENT
                        .requestMatchers("/api/client/**").hasRole("CLIENT")

                        // rutas de EMPLOYEE y superiores (incluye BOSS y SUPERVISOR)
                        .requestMatchers("/api/employee/**").hasRole("EMPLOYEE")

                        // rutas de SUPERVISOR y superiores
                        .requestMatchers("/api/supervisor/**").hasRole("SUPERVISOR")

                        // rutas sólo para BOSS
                        .requestMatchers("/api/boss/**").hasRole("BOSS")

                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());

        return http.build();
    }




    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy(
                "ROLE_BOSS > ROLE_SUPERVISOR\n" +
                        "ROLE_SUPERVISOR > ROLE_EMPLOYEE\n" +
                        "ROLE_EMPLOYEE > ROLE_USER\n" +
                        "ROLE_CLIENT > ROLE_USER"
        );
        return hierarchy;
    }



    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("caprish")
                .password("11234")
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}