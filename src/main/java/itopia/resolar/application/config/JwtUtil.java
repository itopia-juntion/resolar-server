package itopia.resolar.application.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long accessTokenValidityInMilliseconds;

    public JwtUtil(
            @Value("${jwt.secret:mySecretKey123456789mySecretKey123456789}") String secret,
            @Value("${jwt.access-token-validity:3600000}") long accessTokenValidityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
    }

    public String createAccessToken(String username, Long userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public Long getUserIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}