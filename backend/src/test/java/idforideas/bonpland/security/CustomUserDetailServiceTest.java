package idforideas.bonpland.security;

import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.UsuarioNoEncontradoException;
import idforideas.bonpland.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static idforideas.bonpland.utils.TestUtil.getUsuario;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;
    @InjectMocks
    CustomUserDetailService userDetailService;

    @Test
    void deberiaBuscarUsuarioPorSuCorreo() {
        //GIVEN
        String correo = "correo@test.com";
        Usuario usuario = getUsuario();
        usuario.setCorreo(correo);
        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));

        //WHEN
        UserDetails userDetails = userDetailService.loadUserByUsername(correo);

        //THEN
        assertNotNull(userDetails);
        assertEquals(correo, userDetails.getUsername());

        verify(usuarioRepository).findByCorreo(correo);
    }

    @Test
    void deberiaLanzarExcepcion_cuandoElUsuarioNoSeEncuentra() {
        //GIVEN
        String correo = "correo@test.com";
        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.empty());

        //WHEN
        Executable executable = () -> userDetailService.loadUserByUsername(correo);

        //THEN
        UsuarioNoEncontradoException e = assertThrows(UsuarioNoEncontradoException.class, executable);
        assertEquals("Usuario no encontrado", e.getMessage());
    }
}