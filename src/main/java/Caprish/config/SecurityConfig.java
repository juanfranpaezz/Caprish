package Caprish.config;

import Caprish.Service.others.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
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
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/swagger-ui/", "/v3/api-docs/", "/swagger-resources/", "/webjars/")
                                .permitAll()

                                .requestMatchers("/credential/login").permitAll()
                                .requestMatchers("/credential/sign-up").permitAll()
                                .requestMatchers("/credential/updateFirstName").hasRole("USER")
                                .requestMatchers("/credential/updateLastName").hasRole("USER")
                                .requestMatchers("/credential/verify-token").permitAll()
                                .requestMatchers("/credential/complete-data").permitAll()
                                .requestMatchers("/credential/updatePassword").hasRole("USER")

                                .requestMatchers("/client/create").permitAll()
                                .requestMatchers("/client/update-phone").hasRole("CLIENT")
                                .requestMatchers("/client/update-tax").hasRole("CLIENT")
                                .requestMatchers("/client/{username}").hasRole("EMPOLYEE")
                                .requestMatchers("/client/all").hasRole("EMPOLYEE")
                                .requestMatchers("/client/delete").hasRole("CLIENT")

                                .requestMatchers("/item/staff/add-from-sale")           .hasRole("EMPLOYEE")
                                .requestMatchers("/item/staff/update-quantity/")     .hasRole("EMPLOYEE")
                                .requestMatchers("/item/staff/delete/")              .hasRole("EMPLOYEE")
                                .requestMatchers("/item/client/add-from-purchase")     .hasRole("CLIENT")
                                .requestMatchers("/item/client/update-quantity/")    .hasRole("CLIENT")
                                .requestMatchers("/item/client/delete/")             .hasRole("CLIENT")


                                .requestMatchers("/cart/create").hasRole("EMPLOYEE")
                                .requestMatchers("/cart/delete/{id}").hasRole("EMPLOYEE")
//                        .requestMatchers("/cart/staff/view/my-sales").hasRole("EMPLOYEE")
//                        .requestMatchers("/cart/client/view/my-purchases/{idBusiness}").hasRole("CLIENT")
                                .requestMatchers("/cart/staff/confirm-sale/").hasRole("EMPLOYEE")
                                .requestMatchers("/cart/client/confirm-purchase").hasRole("CLIENT")

                                .requestMatchers("/chat/{name}").hasRole("USER")
                                .requestMatchers("/message/create").hasRole("USER")


                                .requestMatchers("/product/create").hasRole("SUPERVISOR")
                                .requestMatchers("/product/delete/{name}").hasRole("SUPERVISOR")
                                .requestMatchers("/product/staff/name/{name}").hasRole("EMPLOYEE")
                                .requestMatchers("/product/client/name/{name}").hasRole("CLIENT")
                                .requestMatchers("/product/updateName/{oldName}/{newName}").hasRole("SUPERVISOR")
                                .requestMatchers("/product/updateDescription/{name}/{description}").hasRole("SUPERVISOR")
                                .requestMatchers("/product/updatePrice/{name}/{price}").hasRole("SUPERVISOR")
                                .requestMatchers("/product/all").permitAll()
                                .requestMatchers("/product/all-by-business/{businessName}").permitAll()
                                .requestMatchers("/product/all-by-my-business").hasRole("EMPLOYEE")

                                .requestMatchers("/business/create").hasRole("BOSS")
                                .requestMatchers("/business/view-my").hasRole("BOSS")
                                .requestMatchers("/business/delete").hasRole("BOSS")
                                .requestMatchers("/business/{name}").hasRole("CLIENT")
                                .requestMatchers("/business/updateBusinessName").hasRole("BOSS")
                                .requestMatchers("/business/updateDescription").hasRole("BOSS")
                                .requestMatchers("/business/updateSlogan").hasRole("BOSS")
                                .requestMatchers("/business/updateTax").hasRole("BOSS")


                                .requestMatchers("/staff/create").hasRole("BOSS")
                                .requestMatchers("/staff/delete/{id}").hasRole("BOSS")
                                .requestMatchers("/promote/{username}").hasRole("BOSS")
                                .requestMatchers("/staff/all").hasRole("BOSS")

                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider(myUserDetailsService))
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