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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import org.springframework.http.HttpMethod;

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


                        .requestMatchers("/credential/login").permitAll()
                        .requestMatchers("/client/delete").hasRole("CLIENT")
                        .requestMatchers("/client/create").hasRole("CLIENT")
                        .requestMatchers("/client/view-my-account").hasRole("CLIENT")
                        .requestMatchers("/business/delete-my-business").hasRole("BOSS")
                        .requestMatchers(HttpMethod.PUT, "/business/updateDescription").hasRole("BOSS")
                        .requestMatchers(HttpMethod.PUT, "/business/updateBusinessName").hasRole("BOSS")
                        .requestMatchers(HttpMethod.PUT, "/business/updateSlogan").hasRole("BOSS")
                        .requestMatchers(HttpMethod.PUT, "/business/updateTax").hasRole("BOSS")
                        .requestMatchers("/business/create").hasRole("BOSS")
                        .requestMatchers("/staff/create").hasRole("BOSS")
                        .requestMatchers("/staff/delete").hasRole("BOSS")
                        .requestMatchers("/staff/promote").hasRole("BOSS")
                        .requestMatchers("/staff/create-boss").hasRole("BOSS")
                        .requestMatchers("/staff/by-business/{businessId}").hasRole("BOSS")

                        .requestMatchers("/product/updateName/{oldName}/{newName}").hasRole("SUPERVISOR")
                        .requestMatchers("/product/updateDescription/{name}/{description}").hasRole("SUPERVISOR")
                        .requestMatchers("/product/updatePrice/{name}/{price}").hasRole("SUPERVISOR")
                        .requestMatchers("/product/create").hasRole("SUPERVISOR")
                        .requestMatchers("/product/delete/{name}").hasRole("SUPERVISOR")

                        .requestMatchers("/client/{username}").hasRole("EMPLOYEE")
                        .requestMatchers("/client/all").hasRole("EMPLOYEE")
                        .requestMatchers("/item/staff/add-from-sale").hasRole("EMPLOYEE")
                        .requestMatchers("/item/staff/update-quantity/{itemId}/{quantity}").hasRole("EMPLOYEE")
                        .requestMatchers("/item/staff/delete/{itemId}").hasRole("EMPLOYEE")
                        .requestMatchers("/cart/create").hasRole("EMPLOYEE")
                        .requestMatchers("/cart/delete/{id}").hasRole("EMPLOYEE")
                        .requestMatchers("/cart/staff/view/my-sales/{businessId}").hasRole("EMPLOYEE")
                        .requestMatchers("/cart/staff/confirm-sale/{cartId}").hasRole("EMPLOYEE")
                        .requestMatchers("/product/staff/name/{name}").hasRole("EMPLOYEE")
                        .requestMatchers("/business/view-my").hasRole("EMPLOYEE")
                        .requestMatchers("/staff/view-my-account").hasRole("EMPLOYEE")
                        .requestMatchers("/product/all-by-my-business").hasRole("EMPLOYEE")

                        .requestMatchers("/item/client/add-from-purchase").hasRole("CLIENT")
                        .requestMatchers("/item/client/update-quantity/{itemId}/{quantity}").hasRole("CLIENT")
                        .requestMatchers("/client/update-phone/{phone}").hasRole("CLIENT")
                        .requestMatchers("/client/update-tax/{tax}").hasRole("CLIENT")
                        .requestMatchers("/item/client/delete/{itemId}").hasRole("CLIENT")
                        .requestMatchers("/cart/client/view/my-purchases").hasRole("CLIENT")
                        .requestMatchers("/cart/client/confirm-purchase").hasRole("CLIENT")
                        .requestMatchers("/product/client/name/{name}").hasRole("CLIENT")
                        .requestMatchers("/business/{name}").hasRole("CLIENT")



                        .requestMatchers("/credential/logout").hasRole("USER")
                        .requestMatchers("/credential/updateFirstName").hasRole("USER")
                        .requestMatchers("/credential/updateLastName").hasRole("USER")
                        .requestMatchers("/credential/complete-data").hasRole("USER")
                        .requestMatchers("/credential/updatePassword").hasRole("USER")
                        .requestMatchers("/chat/{name}").hasRole("USER")
                        .requestMatchers("/message/send").hasRole("USER")
                        .requestMatchers("/product/show-product").hasRole("USER")

                        .requestMatchers("/swagger-ui/*", "/v3/api-docs/", "/swagger-resources/", "/webjars/*","/auth/login")
                        .permitAll()

                        .requestMatchers("/product/all").permitAll()

                        .requestMatchers("/credential/sign-up").permitAll()
                        .requestMatchers("/credential/verify-token").permitAll()
                        .requestMatchers("/product/all-by-business/{businessName}").permitAll()

                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .authenticationProvider(authenticationProvider(myUserDetailsService))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                );

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