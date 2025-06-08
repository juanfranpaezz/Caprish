package Caprish.config;

import Caprish.Service.others.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired private DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter,
            MyUserDetailsService myUserDetailsService) throws Exception {

        http
                // 1) DESACTIVO CSRF porque uso JWT
                .csrf(csrf -> csrf.disable())

                // 2) “Stateless” para JWT: no usamos sesión HTTP
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3) Configuración de rutas:
                .authorizeHttpRequests(auth -> auth
                        // Permito llamar libremente a /auth/login para obtener el JWT
                        .requestMatchers("/auth/login").permitAll()
//                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**")
//                        .permitAll()
//                        .requestMatchers("/message/all").hasRole("ADMIN")
//                        .requestMatchers("/message/create").hasRole("USER")
//                        .requestMatchers("/message/delete/{id}").hasRole("ADMIN")
//                        .requestMatchers("/message/{id}").hasRole("ADMIN")
//                        .requestMatchers("/message/updateChatId/{id}/{chatId}").hasRole("ADMIN")
//                        .requestMatchers("/message/updateSenderId/{id}/{senderId}").hasRole("ADMIN")
//                        .requestMatchers("/message/updateContent/{id}/{content}").hasRole("ADMIN")
//                        .requestMatchers("/message/updateSentAt/{id}/{timestamp}").hasRole("ADMIN")
//                        .requestMatchers("/message/all").hasRole("USER")
//
//                        /* STAFF (employee) */
//                        .requestMatchers("/employee").hasRole("ADMIN")
//                        .requestMatchers("/employee/create").hasRole("ADMIN")
//                        .requestMatchers("/employee/delete/{id}").hasRole("ADMIN")
//                        .requestMatchers("/employee/updateBusinessId/{id}/{businessId}").hasRole("ADMIN")
//                        .requestMatchers("/employee/updateWorkRole/{id}/{workRole}").hasRole("ADMIN")
//                        .requestMatchers("/employee/{id}").hasRole("ADMIN")
//                        .requestMatchers("/employee/all").hasRole("ADMIN")
//
//                        /* USER-GENERIC */
//                        .requestMatchers("/user/log").hasRole("USER")
//                        .requestMatchers("/user/updateFirstName/{id}/{firstName}").hasRole("USER")
//                        .requestMatchers("/user/updateLastName/{id}/{lastName}").hasRole("USER")
//                        .requestMatchers("/user/updateEmail/{id}/{email}").hasRole("USER")
//                        .requestMatchers("/user/updatePasswordHash/{id}/{passwordHash}").hasRole("USER")
//                        .requestMatchers("/user/updateRoleId/{id}/{roleId}").hasRole("BOSS")
//                        .requestMatchers("/user/sign-up").hasRole("USER")
//
//                        /* CLIENT */
//                        .requestMatchers("/client/create").hasRole("USER")
//                        .requestMatchers("/client/delete/{id}").hasRole("ADMIN")
//                        .requestMatchers("/client/updatePhone/{id}/{phone}").hasRole("CLIENT")
//                        .requestMatchers("/client/updateTax/{id}/{tax}").hasRole("CLIENT")
//                        .requestMatchers("/client/{id}").hasRole("CLIENT")
//                        .requestMatchers("/client/all").hasRole("ADMIN")
//
//                        /* ROLE */
//                        .requestMatchers("/role").hasRole("ADMIN")
//                        .requestMatchers("/role/create").hasRole("ADMIN")
//                        .requestMatchers("/role/delete/{id}").hasRole("ADMIN")
//                        .requestMatchers("/role/{id}").hasRole("ADMIN")
//                        .requestMatchers("/role/updateName/{id}/{name}").hasRole("ADMIN")
//                        .requestMatchers("/role/all").hasRole("ADMIN")
//
//                        /* ITEM (cart_item) */
//                        .requestMatchers("/cart_item/create").hasRole("CLIENT")
//                        .requestMatchers("/cart_item/delete/{id}").hasRole("CLIENT")
//                        .requestMatchers("/cart_item/{id}").hasRole("CLIENT")
//                        .requestMatchers("/cart_item/updateCartId/{id}/{cartId}").hasRole("CLIENT")
//                        .requestMatchers("/cart_item/updateProductId/{id}/{productId}").hasRole("CLIENT")
//                        .requestMatchers("/cart_item/updateQuantity/{id}/{quantity}").hasRole("CLIENT")
//                        .requestMatchers("/cart_item/all").hasRole("CLIENT")
//
//                        /* CART */
//                        .requestMatchers("/cart/create").hasRole("CLIENT")
//                        .requestMatchers("/cart/delete/{id}").hasRole("CLIENT")
//                        .requestMatchers("/cart/{id}").hasRole("EMPLOYEE")
//                        .requestMatchers("/cart/updateCartType/{id}/{type}").hasRole("USER")
//                        .requestMatchers("/cart/updateClientId/{id}/{clientId}").hasRole("EMPLOYEE")
//                        .requestMatchers("/cart/updateCartStatus/{id}/{status}").hasRole("SUPERVISOR")
//                        .requestMatchers("/cart/updateStaffId/{id}/{staffId}").hasRole("SUPERVISOR")
//                        .requestMatchers("/cart/updateSaleDate/{id}/{date}").hasRole("EMPLOYEE")
//                        .requestMatchers("/cart/all").hasRole("EMPLOYEE")
//
//                        /* CHAT */
//                        .requestMatchers("/chat").hasRole("ADMIN")
//                        .requestMatchers("/chat/create").hasRole("ADMIN")
//                        .requestMatchers("/chat/delete/{id}").hasRole("ADMIN")
//                        .requestMatchers("/chat/{id}").hasRole("ADMIN")
//                        .requestMatchers("/chat/updateBusinessId/{id}/{businessId}").hasRole("ADMIN")
//                        .requestMatchers("/chat/updateClientId/{id}/{clientId}").hasRole("ADMIN")
//                        .requestMatchers("/chat/all").hasRole("ADMIN")
//
//                        /* STOCK */
//                        .requestMatchers("/stock/create").hasRole("EMPLOYEE")
//                        .requestMatchers("/stock/delete/{id}").hasRole("EMPLOYEE")
//                        .requestMatchers("/stock/{id}").hasRole("EMPLOYEE")
//                        .requestMatchers("/stock/updateProductId/{id}/{productId}").hasRole("EMPLOYEE")
//                        .requestMatchers("/stock/updateBranchId/{id}/{branchId}").hasRole("EMPLOYEE")
//                        .requestMatchers("/stock/updateQuantity/{id}/{quantity}").hasRole("EMPLOYEE")
//                        .requestMatchers("/stock/all").hasRole("EMPLOYEE")
//
//                        /* PRODUCT */
//                        .requestMatchers("/product/create").hasRole("EMPLOYEE")
//                        .requestMatchers("/product/delete/{id}").hasRole("EMPLOYEE")
//                        .requestMatchers("/product/{id}").hasRole("CLIENT")
//                        .requestMatchers("/product/updateBusinessId/{id}/{businessId}").hasRole("EMPLOYEE")
//                        .requestMatchers("/product/updateName/{id}/{name}").hasRole("EMPLOYEE")
//                        .requestMatchers("/product/updateBarCode/{id}/{barCode}").hasRole("EMPLOYEE")
//                        .requestMatchers("/product/updateDescription/{id}/{description}").hasRole("EMPLOYEE")
//                        .requestMatchers("/product/updatePrice/{id}/{price}").hasRole("EMPLOYEE")
//                        .requestMatchers("/product/all").hasRole("CLIENT")
//
//                        /* BUSINESS */
//                        .requestMatchers("/business/create").hasRole("BOSS")
//                        .requestMatchers("/business/delete/{id}").hasRole("BOSS")
//                        .requestMatchers("/business/{id}").hasRole("BOSS")
//                        .requestMatchers("/business/updateBusinessName/{id}/{name}").hasRole("BOSS")
//                        .requestMatchers("/business/updateDescription/{id}/{description}").hasRole("BOSS")
//                        .requestMatchers("/business/updateSlogan/{id}/{slogan}").hasRole("BOSS")
//                        .requestMatchers("/business/updateTax/{id}/{tax}").hasRole("BOSS")
//                        .requestMatchers("/business/all").hasRole("BOSS")
//
//                        /* BRANCH */
//                        .requestMatchers("/branch/create").hasRole("BOSS")
//                        .requestMatchers("/branch/delete/{id}").hasRole("BOSS")
//                        .requestMatchers("/branch/updateBusinessId/{id}/{businessId}").hasRole("ADMIN")
//                        .requestMatchers("/branch/updateAddress/{id}/{address}").hasRole("BOSS")
//                        .requestMatchers("/branch/updateBranchType/{id}/{type}").hasRole("BOSS")
//                        .requestMatchers("/branch/{id}").hasRole("BOSS")
//                        .requestMatchers("/branch/all").hasRole("BOSS")
//
//                        /* CLIENT_REPORT */
//                        .requestMatchers("/client_report/create").hasRole("CLIENT")
//                        .requestMatchers("/client_report/delete/{id}").hasRole("ADMIN")
//                        .requestMatchers("/client_report/{id}").hasRole("ADMIN")
//                        .requestMatchers("/client_report/all").hasRole("ADMIN")
//
//                        /* BUSINESS_REPORT */
//                        .requestMatchers("/business_report/create").hasRole("EMPLOYEE")
//                        .requestMatchers("/business_report/delete/{id}").hasRole("ADMIN")
//                        .requestMatchers("/business_report/{id}").hasRole("ADMIN")
//                        .requestMatchers("/business_report/all").hasRole("ADMIN")
//
                        .anyRequest().authenticated()
                )

                // 5) Proveedor de autenticación (DaoAuthenticationProvider apuntando a MyUserDetailsService)
                .authenticationProvider(authenticationProvider(myUserDetailsService))

                // 6) Agrego el filtro JWT antes de UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ------------------------------------------------------------

    @Bean
    public UserDetailsService userDetailsService() {
        // Este bean NO será usado directamente por DaoAuthenticationProvider,
        // porque en AuthenticationProvider inyecto MyUserDetailsService.
        // Lo bueno es que lo defino por si otro componente lo necesita.
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // Consulta para cargar username/password/enabled:
        manager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM credential WHERE username = ?"
        );

        // Consulta para cargar autoridades: ojo al alias “authority”
        // Esta columna debe devolver “ROLE_ADMIN”, “ROLE_USER”, etc.
        manager.setAuthoritiesByUsernameQuery(
                "SELECT username, id_role AS authority FROM credential WHERE username = ?"
        );

        return manager;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(MyUserDetailsService myUserDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Jerarquía de roles (opcional):
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("""
            ROLE_ADMIN > ROLE_USER
            ROLE_BOSS > ROLE_SUPERVISOR
            ROLE_SUPERVISOR > ROLE_EMPLOYEE
            ROLE_EMPLOYEE > ROLE_USER
            ROLE_CLIENT > ROLE_USER
        """);
        return hierarchy;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
