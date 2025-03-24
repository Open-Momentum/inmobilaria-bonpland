package idforideas.bonpland.security;

import com.mysql.cj.jdbc.Blob;
import idforideas.bonpland.entities.Rol;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static idforideas.bonpland.utils.TestUtil.getUsuario;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @InjectMocks
    private JwtService jwtService;
    private CustomUserDetails customUserDetails;

    @BeforeEach
    void init() {
        customUserDetails = new CustomUserDetails(getUsuario());
    }


    @Test
    void deberiaGenerarUnToken() {
        //WHEN
        String token = jwtService.generarToken(customUserDetails);

        //THEN
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void deberiaGenerarElMismoTokenConElMismoInput() {
        //WHEN
        String token1 = jwtService.generarToken(customUserDetails);
        String token2 = jwtService.generarToken(customUserDetails);

        //THEN
        assertEquals(token1, token2);
    }

    @Test
    void deberiaValidarTokenCorrecto(){
        //GIVEN
        String token = jwtService.generarToken(customUserDetails);

        //WHEN
        boolean resultado = jwtService.esTokenValido(token, customUserDetails);

        //THEN
        assertTrue(resultado);
    }

    @Test
    void deberiaValidarTokenExpirado(){
        //GIVEN
        String token = jwtService.generarToken(customUserDetails,-1L);

        //WHEN
        Executable executable = ()-> jwtService.esTokenValido(token, customUserDetails);

        //THEN
        JwtException e = assertThrows(JwtException.class, executable);
        assertEquals("El token ha expirado", e.getMessage());
    }
}
