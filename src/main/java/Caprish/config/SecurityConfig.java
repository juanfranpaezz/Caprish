package Caprish.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                .logout(withDefaults());

        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
        var manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery(
                "SELECT email AS username, password_hash AS password, TRUE AS enabled " +
                        "FROM ( " +
                        "  SELECT email, password_hash FROM staff " +
                        "  UNION ALL " +
                        "  SELECT email, password_hash FROM client " +
                        "  UNION ALL " +
                        "  SELECT email, password_hash FROM platform_admin " +
                        ") u WHERE u.email = ?"
        );
        manager.setAuthoritiesByUsernameQuery(
                "SELECT u.email AS username, r.name AS authority " +
                        "FROM ( " +
                        "  SELECT email, role_id FROM staff " +
                        "  UNION ALL " +
                        "  SELECT email, role_id FROM client " +
                        "  UNION ALL " +
                        "  SELECT email, role_id FROM platform_admin " +
                        ") u " +
                        "JOIN role r ON u.role_id = r.id " +
                        "WHERE u.email = ?"
        );
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        var hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy(
                "ROLE_BOSS > ROLE_SUPERVISOR\n" +
                        "ROLE_SUPERVISOR > ROLE_EMPLOYEE\n" +
                        "ROLE_EMPLOYEE > ROLE_USER\n" +
                        "ROLE_CLIENT > ROLE_USER"
        );
        return hierarchy;
    }
}
