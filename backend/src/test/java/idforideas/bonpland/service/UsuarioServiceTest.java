package idforideas.bonpland.service;

import idforideas.bonpland.dto.UsuarioDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.CorreoExistenteException;
import idforideas.bonpland.repository.UsuarioRepository;
import static org.junit.jupiter.api.Assertions.*;

import idforideas.bonpland.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    UsuarioDTO dto;
    Usuario usuario;

    @BeforeEach
    void setUp() {
        dto = getUsuarioDTO();
        usuario = getUsuario();
    }

    @Test
    void deberiaGuardarUsuario_cuandoLosDatosSonValidos(){
        //GIVEN
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer((invocation)->{
            usuario.setId(1L);
            return usuario;
        });

        //WHEN
        Usuario usuarioGuardado = usuarioService.guardarUsuario(dto);

        //THEN
        assertNotNull(usuarioGuardado);
        assertNotNull(usuarioGuardado.getId());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deberiaLanzarExcepcionAlGuardarUsuario_cuandoElCorreoYaExiste(){
        //GIVEN
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));

        //WHEN
        Executable executable = () -> usuarioService.guardarUsuario(dto);

        //THEN
        CorreoExistenteException e = assertThrows(CorreoExistenteException.class, executable);
        assertEquals("El correo ya existe", e.getMessage());

    }

    private  UsuarioDTO getUsuarioDTO() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("test");
        dto.setApellido("test");
        dto.setTelefono("+541122334455");
        dto.setClave("clave.secreta#2");
        dto.setCorreo("test@mail.com");
        return dto;
    }

    private  Usuario getUsuario() {
        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId(1L);
        usuarioEsperado.setNombre("test");
        usuarioEsperado.setApellido("test");
        usuarioEsperado.setTelefono("+541122334455");
        usuarioEsperado.setClave("clave.secreta#2");
        usuarioEsperado.setCorreo("test@mail.com");
        usuarioEsperado.setRol(new Rol());
        return usuarioEsperado;
    }
}