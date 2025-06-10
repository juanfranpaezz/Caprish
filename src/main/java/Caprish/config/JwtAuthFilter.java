package Caprish.config;

import Caprish.Service.others.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;  // suponemos que tenés un JwtService

    public JwtAuthFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1) Dejo pasar la petición si es OPTIONS (CORS preflight)
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2) Dejo pasar el login sin tocar el token
        String path = request.getRequestURI();
        if (path.startsWith("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3) Tomo el header Authorization
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            // No hay token, simplemente sigo el chain: al final Spring rechazará la request si no hay auth
            filterChain.doFilter(request, response);
            return;
        }

        // 4) Extraigo el token y lo valido
        String token = header.substring(7);
        String username = jwtService.extractUsername(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Cargo el usuario desde la DB (o desde JWT si guardás allí roles/claims)
            var userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails)) {
                // Seteo la autenticación en el contexto
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 5) Continúo la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
