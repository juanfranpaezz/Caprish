package Caprish.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
                        /*message*/
                        .requestMatchers("/message/all").hasRole("ADMIN")
                        .requestMatchers("/message/create").hasRole("USER")
                        .requestMatchers("/message/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/message/{id}").hasRole("ADMIN")
                        .requestMatchers("/message/updateChatId/{id}/{chatId}").hasRole("ADMIN")
                        .requestMatchers("/message/updateSenderId/{id}/{senderId}").hasRole("ADMIN")
                        .requestMatchers("/message/updateContent/{id}/{content}").hasRole("ADMIN")
                        .requestMatchers("/message/updateSentAt/{id}/{timestamp}").hasRole("ADMIN")
                        .requestMatchers("/message/all").hasRole("USER")

                        /* STAFF (employee) */
                        .requestMatchers("/employee").hasRole("ADMIN")
                        .requestMatchers("/employee/create").hasRole("ADMIN")
                        .requestMatchers("/employee/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/employee/updateBusinessId/{id}/{businessId}").hasRole("ADMIN")
                        .requestMatchers("/employee/updateWorkRole/{id}/{workRole}").hasRole("ADMIN")
                        .requestMatchers("/employee/{id}").hasRole("ADMIN")
                        .requestMatchers("/employee/all").hasRole("ADMIN")

                        /* USER-GENERIC */
                        .requestMatchers("/user/log").hasRole("USER")
                        .requestMatchers("/user/updateFirstName/{id}/{firstName}").hasRole("USER")
                        .requestMatchers("/user/updateLastName/{id}/{lastName}").hasRole("USER")
                        .requestMatchers("/user/updateEmail/{id}/{email}").hasRole("USER")
                        .requestMatchers("/user/updatePasswordHash/{id}/{passwordHash}").hasRole("USER")
                        .requestMatchers("/user/updateRoleId/{id}/{roleId}").hasRole("BOSS")
                        .requestMatchers("/user/sign-up").hasRole("USER")

                        /* CLIENT */
                        .requestMatchers("/client/create").hasRole("USER")
                        .requestMatchers("/client/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/client/updatePhone/{id}/{phone}").hasRole("CLIENT")
                        .requestMatchers("/client/updateTax/{id}/{tax}").hasRole("CLIENT")
                        .requestMatchers("/client/{id}").hasRole("CLIENT")
                        .requestMatchers("/client/all").hasRole("ADMIN")

                        /* ROLE */
                        .requestMatchers("/role").hasRole("ADMIN")
                        .requestMatchers("/role/create").hasRole("ADMIN")
                        .requestMatchers("/role/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/role/{id}").hasRole("ADMIN")
                        .requestMatchers("/role/updateName/{id}/{name}").hasRole("ADMIN")
                        .requestMatchers("/role/all").hasRole("ADMIN")

                        /* ITEM (cart_item) */
                        .requestMatchers("/cart_item/create").hasRole("CLIENT")
                        .requestMatchers("/cart_item/delete/{id}").hasRole("CLIENT")
                        .requestMatchers("/cart_item/{id}").hasRole("CLIENT")
                        .requestMatchers("/cart_item/updateCartId/{id}/{cartId}").hasRole("CLIENT")
                        .requestMatchers("/cart_item/updateProductId/{id}/{productId}").hasRole("CLIENT")
                        .requestMatchers("/cart_item/updateQuantity/{id}/{quantity}").hasRole("CLIENT")
                        .requestMatchers("/cart_item/all").hasRole("CLIENT")

                        /* CART */
                        .requestMatchers("/cart/create").hasRole("CLIENT")
                        .requestMatchers("/cart/delete/{id}").hasRole("CLIENT")
                        .requestMatchers("/cart/{id}").hasRole("EMPLOYEE")
                        .requestMatchers("/cart/updateCartType/{id}/{type}").hasRole("USER")
                        .requestMatchers("/cart/updateClientId/{id}/{clientId}").hasRole("EMPLOYEE")
                        .requestMatchers("/cart/updateCartStatus/{id}/{status}").hasRole("SUPERVISOR")
                        .requestMatchers("/cart/updateStaffId/{id}/{staffId}").hasRole("SUPERVISOR")
                        .requestMatchers("/cart/updateSaleDate/{id}/{date}").hasRole("EMPLOYEE")
                        .requestMatchers("/cart/all").hasRole("EMPLOYEE")

                        /* CHAT */
                        .requestMatchers("/chat").hasRole("ADMIN")
                        .requestMatchers("/chat/create").hasRole("ADMIN")
                        .requestMatchers("/chat/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/chat/{id}").hasRole("ADMIN")
                        .requestMatchers("/chat/updateBusinessId/{id}/{businessId}").hasRole("ADMIN")
                        .requestMatchers("/chat/updateClientId/{id}/{clientId}").hasRole("ADMIN")
                        .requestMatchers("/chat/all").hasRole("ADMIN")

                        /* STOCK */
                        .requestMatchers("/stock/create").hasRole("EMPLOYEE")
                        .requestMatchers("/stock/delete/{id}").hasRole("EMPLOYEE")
                        .requestMatchers("/stock/{id}").hasRole("EMPLOYEE")
                        .requestMatchers("/stock/updateProductId/{id}/{productId}").hasRole("EMPLOYEE")
                        .requestMatchers("/stock/updateBranchId/{id}/{branchId}").hasRole("EMPLOYEE")
                        .requestMatchers("/stock/updateQuantity/{id}/{quantity}").hasRole("EMPLOYEE")
                        .requestMatchers("/stock/all").hasRole("EMPLOYEE")

                        /* PRODUCT */
                        .requestMatchers("/product/create").hasRole("EMPLOYEE")
                        .requestMatchers("/product/delete/{id}").hasRole("EMPLOYEE")
                        .requestMatchers("/product/{id}").hasRole("CLIENT")
                        .requestMatchers("/product/updateBusinessId/{id}/{businessId}").hasRole("EMPLOYEE")
                        .requestMatchers("/product/updateName/{id}/{name}").hasRole("EMPLOYEE")
                        .requestMatchers("/product/updateBarCode/{id}/{barCode}").hasRole("EMPLOYEE")
                        .requestMatchers("/product/updateDescription/{id}/{description}").hasRole("EMPLOYEE")
                        .requestMatchers("/product/updatePrice/{id}/{price}").hasRole("EMPLOYEE")
                        .requestMatchers("/product/all").hasRole("CLIENT")

                        /* BUSINESS */
                        .requestMatchers("/business/create").hasRole("BOSS")
                        .requestMatchers("/business/delete/{id}").hasRole("BOSS")
                        .requestMatchers("/business/{id}").hasRole("BOSS")
                        .requestMatchers("/business/updateBusinessName/{id}/{name}").hasRole("BOSS")
                        .requestMatchers("/business/updateDescription/{id}/{description}").hasRole("BOSS")
                        .requestMatchers("/business/updateSlogan/{id}/{slogan}").hasRole("BOSS")
                        .requestMatchers("/business/updateTax/{id}/{tax}").hasRole("BOSS")
                        .requestMatchers("/business/all").hasRole("BOSS")

                        /* BRANCH */
                        .requestMatchers("/branch/create").hasRole("BOSS")
                        .requestMatchers("/branch/delete/{id}").hasRole("BOSS")
                        .requestMatchers("/branch/updateBusinessId/{id}/{businessId}").hasRole("ADMIN")
                        .requestMatchers("/branch/updateAddress/{id}/{address}").hasRole("BOSS")
                        .requestMatchers("/branch/updateBranchType/{id}/{type}").hasRole("BOSS")
                        .requestMatchers("/branch/{id}").hasRole("BOSS")
                        .requestMatchers("/branch/all").hasRole("BOSS")

                        /* CLIENT_REPORT */
                        .requestMatchers("/client_report/create").hasRole("CLIENT")
                        .requestMatchers("/client_report/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/client_report/{id}").hasRole("ADMIN")
                        .requestMatchers("/client_report/all").hasRole("ADMIN")

                        /* BUSINESS_REPORT */
                        .requestMatchers("/business_report/create").hasRole("EMPLOYEE")
                        .requestMatchers("/business_report/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/business_report/{id}").hasRole("ADMIN")
                        .requestMatchers("/business_report/all").hasRole("ADMIN")

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
                "ROLE_ADMIN > ROLE_USER\n" +
                "ROLE_BOSS > ROLE_SUPERVISOR\n" +
                        "ROLE_SUPERVISOR > ROLE_EMPLOYEE\n" +
                        "ROLE_EMPLOYEE > ROLE_USER\n" +
                        "ROLE_CLIENT > ROLE_USER"
        );
        return hierarchy;
    }
}
