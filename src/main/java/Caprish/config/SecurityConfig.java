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

                        // Endpoint público sin auth
                        .requestMatchers("/product/all", "/api/publico").permitAll()

                        // Swagger (público)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**")
                        .permitAll()

                        // Rutas que requieren rol específico:
                        .requestMatchers("/api/user/**").hasRole("USER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/message/view/**").hasRole("ADMIN")

                        // Cualquier otra petición requiere autenticado
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
