package idforideas.bonpland.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Figueroa Mauro
 */
@Service
public class JwtService {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    public String generarToken(CustomUserDetails userDetails, Long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("rol", userDetails.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String generarToken(CustomUserDetails userDetails) {
        return generarToken(userDetails, EXPIRATION_TIME);
    }


    public boolean esTokenValido(String token, UserDetails userDetails) {
        String username = extraerCorreo(token);

        return username.equals(userDetails.getUsername()) && !esTokenExpirado(token);
    }

    public String extraerCorreo(String token) {
        return extraerClaims(token, Claims::getSubject);
    }

    private boolean esTokenExpirado(String token) {
        return extraerClaims(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extraerClaims(String token, Function<Claims, T> claimsResolver) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claimsResolver.apply(claims);
        } catch (ExpiredJwtException e) {
            throw new JwtException("El token ha expirado", e);
        } catch (
                SignatureException e) {
            throw new JwtException("Firma inválida del token", e);
        } catch (JwtException e) {
            throw new JwtException("Token inválido", e);
        }
    }

}