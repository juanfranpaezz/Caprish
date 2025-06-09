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
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**")
                        .permitAll()


                        /*MESSAGE*/
                        .requestMatchers("/message/create").hasRole("USER")
//                        .requestMatchers("/message/all").hasRole("USER")

                        /* CREDENTIAL */
//                        .requestMatchers("/user/log").hasRole("USER")
                        .requestMatchers("/credential/updateFirstName/{id}/{firstName}").hasRole("USER")
                        .requestMatchers("/credential/updateLastName/{id}/{lastName}").hasRole("USER")
                        .requestMatchers("/credential/updatePassword/{id}/{password}").hasRole("USER")
//                        .requestMatchers("/credential/updateRoleId/{id}/{roleId}").hasRole("BOSS")
//                        .requestMatchers("/credential/create").hasRole("USER")

                        /* CLIENT */
//                        .requestMatchers("/client/create").permitAll()
                        .requestMatchers("/client/delete/{id}").hasRole("ADMIN")
                        .requestMatchers("/client/updatePhone/{id}/{phone}").hasRole("CLIENT")
                        .requestMatchers("/client/updateTax/{id}/{tax}").hasRole("CLIENT")
//                        .requestMatchers("/client/{id}").hasRole("ADMIN")
                        .requestMatchers("/client/all").hasRole("ADMIN")

                        /* ITEM */
                        .requestMatchers("/item/add").hasRole("USER")
                        .requestMatchers("/item/delete/{id}").hasRole("USER")
                        .requestMatchers("/item/updateQuantity/{id}/{quantity}").hasRole("USER")
                        .requestMatchers("/item/all").hasRole("USER")

                        /* CART */
                        .requestMatchers("/cart/create").hasRole("EMPLOYEE")
//                        .requestMatchers("/cart/confirm-purchase").hasRole("USER")
//                        .requestMatchers("/cart/delete/{id}").hasRole("CLIENT")
//                        .requestMatchers("/cart/{id}").hasRole("EMPLOYEE")
                        .requestMatchers("/cart/updateClientId/{id}/{clientId}").hasRole("EMPLOYEE")
//                        .requestMatchers("/cart/updateCartStatus/{id}/{status}").hasRole("SUPERVISOR")
                        .requestMatchers("/cart/updateStaffId/{id}/{staffId}").hasRole("SUPERVISOR")
//                        .requestMatchers("/cart/all").hasRole("EMPLOYEE")

                        /* CHAT */
//                        .requestMatchers("/chat/{id}").hasRole("ADMIN")

                        /* STOCK */
//                        .requestMatchers("/stock/{id}").hasRole("EMPLOYEE")
                        .requestMatchers("/stock/updateQuantity/{id}/{quantity}").hasRole("EMPLOYEE")
                        .requestMatchers("/stock/all").hasRole("EMPLOYEE")

                        /* PRODUCT */
                        .requestMatchers("/product/create").hasRole("SUPERVISOR")
                        .requestMatchers("/product/delete/{id}").hasRole("SUPERVISOR")
//                        .requestMatchers("/product/{id}").hasRole("CLIENT")
                        .requestMatchers("/product/updateName/{id}/{name}").hasRole("SUPERVISOR")
                        .requestMatchers("/product/updateDescription/{id}/{description}").hasRole("SUPERVISOR")
                        .requestMatchers("/product/updatePrice/{id}/{price}").hasRole("SUPERVISOR")
                        .requestMatchers("/product/all").permitAll()

                        /* BUSINESS */
//                        .requestMatchers("/business/create").permitAll()
//                        .requestMatchers("/business/delete/{id}").hasRole("BOSS")
//                        .requestMatchers("/business/{id}").hasRole("BOSS")
                        .requestMatchers("/business/updateBusinessName/{id}/{name}").hasRole("BOSS")
                        .requestMatchers("/business/updateDescription/{id}/{description}").hasRole("BOSS")
                        .requestMatchers("/business/updateSlogan/{id}/{slogan}").hasRole("BOSS")
                        .requestMatchers("/business/updateTax/{id}/{tax}").hasRole("BOSS")
                        .requestMatchers("/business/all").hasRole("CLIENT")

                        /* BRANCH */
                        .requestMatchers("/branch/create").hasRole("BOSS")
                        .requestMatchers("/branch/delete/{id}").hasRole("BOSS")
//                        .requestMatchers("/branch/updateAddress/{id}/{address}").hasRole("BOSS")
                        .requestMatchers("/branch/updateBranchType/{id}/{type}").hasRole("BOSS")
//                        .requestMatchers("/branch/{id}").hasRole("EMPLOYEE")
                        .requestMatchers("/branch/all").hasRole("EMPLOYEE")

                        /* CLIENT_REPORT */
                        .requestMatchers("/client_report/create").hasRole("CLIENT")
                        .requestMatchers("/client_report/delete/{id}").hasRole("ADMIN")
//                        .requestMatchers("/client_report/{id}").hasRole("ADMIN")

                        /* BUSINESS_REPORT */
                        .requestMatchers("/business_report/create").hasRole("EMPLOYEE")
                        .requestMatchers("/business_report/delete/{id}").hasRole("ADMIN")
//                        .requestMatchers("/business_report/{id}").hasRole("ADMIN")


                                /* STAFF */ /*--> ACA TENDRIAMOS QUE AGARRAR Y HACER TRES CONTROLLERS PARA ORGANIZAR SEGUN TIPO DE STAFF*/
//                        .requestMatchers("/staff/create").hasRole("BOSS")
//                        .requestMatchers("/staff/delete/{id}").hasRole("BOSS")
//                        .requestMatchers("/staff/updateWorkRole/{id}/{workRole}").hasRole("BOSS")
//                        .requestMatchers("/staff/{id}").hasRole("BOSS")
                                .requestMatchers("/staff/all").hasRole("BOSS")


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
            ROLE_ADMIN > ROLE_BOSS
            ROLE_ADMIN > ROLE_CLIENT
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
