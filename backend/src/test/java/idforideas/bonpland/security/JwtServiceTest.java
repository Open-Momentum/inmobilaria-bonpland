package idforideas.bonpland.security;

import idforideas.bonpland.entities.Usuario;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static idforideas.bonpland.utils.TestUtil.getUsuario;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @InjectMocks
    private JwtService jwtService;
    private CustomUserDetails customUserDetails;
    private String tokenFirmaInvalida;


    @BeforeEach
    void init() {
        customUserDetails = new CustomUserDetails(getUsuario());
        tokenFirmaInvalida = generarTokenFirmaInvalida();
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
    void deberiaValidarTokenCorrecto() {
        //GIVEN
        String token = jwtService.generarToken(customUserDetails);

        //WHEN
        boolean resultado = jwtService.esTokenValido(token, customUserDetails);

        //THEN
        assertTrue(resultado);
    }

    @Test
    void deberiaValidarTokenExpirado() {
        //GIVEN
        String token = jwtService.generarToken(customUserDetails, -1L);

        //WHEN
        Executable executable = () -> jwtService.esTokenValido(token, customUserDetails);

        //THEN
        JwtException e = assertThrows(JwtException.class, executable);
        assertEquals("El token ha expirado", e.getMessage());
    }


    @Test
    void deberiaValidarTokenConFirmaInvalida() {
        //WHEN
        Executable executable = () -> jwtService.esTokenValido(tokenFirmaInvalida, customUserDetails);

        //THEN
        JwtException e = assertThrows(JwtException.class, executable);
        assertEquals("Firma inválida del token", e.getMessage());
    }


    @Test
    void deberiaValidarTokenInvalido() {
        //GIVEN
        String token = "Token invalido";

        //WHEN
        Executable executable = () -> jwtService.esTokenValido(token, customUserDetails);

        //THEN
        JwtException e = assertThrows(JwtException.class, executable);
        assertEquals("Token inválido", e.getMessage());
    }

    @Test
    void deberiaValidarTokenDeOtroUsuario() {
        //GIVEN
        String token = jwtService.generarToken(customUserDetails);
        Usuario usuario = getUsuario();
        usuario.setCorreo("OtroCorreo@mail.com");
        CustomUserDetails otroUserDetails = new CustomUserDetails(usuario);

        //WHEN
        boolean resultado = jwtService.esTokenValido(token, otroUserDetails);

        //THEN
        assertFalse(resultado);
    }

    @Test
    void deberiaValidarTokenDeOtroUsuarioYElTokenExpirado() {
        //GIVEN
        String token = jwtService.generarToken(customUserDetails, -1L);
        Usuario usuario = getUsuario();
        usuario.setCorreo("OtroCorreo@mail.com");
        CustomUserDetails otroUserDetails = new CustomUserDetails(usuario);
        AtomicBoolean esValido = new AtomicBoolean(false);

        //WHEN
        Executable executable = () -> {
            esValido.set(jwtService.esTokenValido(token, otroUserDetails));
        };

        //THEN
        JwtException e = assertThrows(JwtException.class, executable);
        assertEquals("El token ha expirado", e.getMessage());
        assertFalse(esValido.get());

    }


    private String generarTokenFirmaInvalida() {
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 36000000))
                .signWith(SECRET_KEY)
                .compact();
    }
}
