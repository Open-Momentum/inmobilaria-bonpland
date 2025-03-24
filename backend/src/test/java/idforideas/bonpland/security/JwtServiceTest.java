package idforideas.bonpland.security;

import idforideas.bonpland.entities.Rol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collections;
import java.util.List;

import static idforideas.bonpland.utils.TestUtil.getUsuario;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

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

}
