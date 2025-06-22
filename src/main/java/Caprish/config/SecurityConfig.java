package Caprish.config;

import Caprish.Service.others.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
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
                        .requestMatchers(
                                "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**",
                                "/auth/login", "/credential/login", "/credential/sign-up", "/credential/verify-token",
                                "/product/all", "/product/all-by-business/{businessName}"
                        ).permitAll()

                        // BOSS endpoints
                        .requestMatchers(
                                "/business/create", "/business/delete", "/business/updateBusinessName/{name}",
                                "/business/updateDescription", "/business/updateSlogan/{slogan}",
                                "/business/updateTax/{tax}", "/staff/create-employee", "/staff/delete/{}",
                                "/staff/promote", "/staff/create-boss", "/staff/by-business/{businessId}"
                        ).hasRole("BOSS")
                        .requestMatchers(HttpMethod.DELETE, "/business/delete-my-business").hasRole("BOSS")

                        // SUPERVISOR endpoints
                        .requestMatchers(
                                "/product/create", "/product/delete/{name}",
                                "/product/updateName/{oldName}/{newName}",
                                "/product/updateDescription/{name}/{description}",
                                "/product/updatePrice/{name}/{price}"
                        ).hasRole("SUPERVISOR")

                        // EMPLOYEE endpoints
                        .requestMatchers(
                                "/client/{username}", "/client/all",
                                "/item/staff/add-from-sale", "/item/staff/update-quantity/{itemId}/{quantity}",
                                "/item/staff/delete/{itemId}", "/cart/create", "/cart/delete/{id}",
                                "/cart/staff/view/my-sales", "/cart/staff/view/my-carts",
                                "/cart/staff/confirm-sale/{cartId}", "/staff/view-my-account",
                                "/product/staff/name/{name}", "/business/view-my"
                        ).hasRole("EMPLOYEE")

                        // CLIENT endpoints
                        .requestMatchers(
                                "/client/complete-data", "/client/update-phone", "/client/update-tax",
                                "/client/delete", "/client/view-my-account",
                                "/item/client/add-from-purchase", "/item/client/update-quantity/{itemId}/{quantity}",
                                "/item/client/delete/{itemId}",
                                "/cart/client/view/my-purchases", "/cart/client/view/my-carts",
                                "/cart/client/confirm-purchase", "/business/{name}",
                                "/product/client/name/{name}"
                        ).hasRole("CLIENT")

                        // USER endpoints
                        .requestMatchers(
                                "/credential/logout", "/credential/updateFirstName", "/credential/updateLastName",
                                "/credential/complete-data", "/credential/updatePassword",
                                "/chat/{name}", "/message/send", "/product/show-product"
                        ).hasRole("USER")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider(myUserDetailsService))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ------------------------------------------------------------

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM credential WHERE username = ?"
        );
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

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("""
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
