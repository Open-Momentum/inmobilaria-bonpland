package idforideas.bonpland.security;

import idforideas.bonpland.entities.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

import static idforideas.bonpland.utils.TestUtil.getUsuario;
import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    @Test
    void deberiaAsignarValoresPorDefectoTrue_cuandoSeInstancia() {
        //GIVEN
        Usuario usuario = getUsuario();

        //WHEN
        CustomUserDetails customUserDetails = new CustomUserDetails(usuario);

        //THEN
        assertNotNull(customUserDetails.getId());
        assertTrue(customUserDetails.isAccountNonExpired());
        assertTrue(customUserDetails.isEnabled());
        assertTrue(customUserDetails.isCredentialsNonExpired());
        assertTrue(customUserDetails.isAccountNonLocked());
        assertEquals("ROLE_USUARIO", customUserDetails.getAuthorities().iterator().next().getAuthority());

    }

}