package Caprish.Service.others;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String secretKey;

    public JwtService(@Qualifier("jwtSecret") String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Genera un token JWT estándar sin campos adicionales.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, null);
    }

    /**
     * Genera un token JWT incluyendo un claim de versión.
     */
    public String generateToken(UserDetails userDetails, Long tokenVersion) {
        var builder = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60));

        if (tokenVersion != null) {
            builder.claim("ver", tokenVersion);
        }

        return builder.signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae un claim genérico del token por nombre y tipo.
     */
    public <T> T extractClaim(String token, String claimName, Class<T> requiredType) {
        Claims claims = extractAllClaims(token);
        Object value = claims.get(claimName);
        if (value == null) {
            return null;
        }
        // Si pidieron Long pero vino Integer (u otro Number), lo convertimos:
        if (requiredType == Long.class && value instanceof Number) {
            Number num = (Number) value;
            return requiredType.cast(Long.valueOf(num.longValue()));
        }
        return requiredType.cast(value);
    }


    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
