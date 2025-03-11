package idforideas.bonpland.service;

import idforideas.bonpland.dto.UsuarioDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.CorreoExistenteException;
import idforideas.bonpland.exception.RolNoEncontradoException;
import idforideas.bonpland.mapper.UsuarioMapper;
//import idforideas.bonpland.mapper.impl.UsuarioMapperImpl;
import idforideas.bonpland.repository.RolRepository;
import idforideas.bonpland.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.*;

import idforideas.bonpland.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * @author Figueroa Mauro
 */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @Spy
    UsuarioMapper usuarioMapper;


    private UsuarioDTO dto;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        dto = getUsuarioDTO();
        usuario = getUsuario();
    }

    @Test
    void deberiaGuardarUsuario_cuandoLosDatosSonValidos() {
        //GIVEN
        when(rolRepository.findByNombre("USUARIO")).thenReturn(Optional.of(new Rol(1L, "USUARIO")));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer((invocation) -> {
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
    void deberiaLanzarExcepcionAlGuardarUsuario_cuandoElCorreoYaExiste() {
        //GIVEN
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));

        //WHEN
        Executable executable = () -> usuarioService.guardarUsuario(dto);

        //THEN
        CorreoExistenteException e = assertThrows(CorreoExistenteException.class, executable);
        assertEquals("El correo ya existe", e.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void deberiaAsignarRolUsuarioPorDefecto_cuandoSeGuardaUnUsuario() {
        //GIVEN
        when(rolRepository.findByNombre("USUARIO")).thenReturn(Optional.of(new Rol(1L, "USUARIO")));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        //WHEN
        Usuario usuarioGuardado = usuarioService.guardarUsuario(dto);

        //THEN
        assertEquals("USUARIO", usuarioGuardado.getRol().getNombre());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deberiaLanzarExcepcion_cuandoNoEncuentraElRol() {
        //GIVEN
        when(rolRepository.findByNombre("USUARIO")).thenReturn(Optional.empty());

        //WHEN
        Executable executable = () -> usuarioService.guardarUsuario(dto);

        //THEN
        RolNoEncontradoException e = assertThrows(RolNoEncontradoException.class, executable);
        assertEquals("Rol no encontrado", e.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    private UsuarioDTO getUsuarioDTO() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("test");
        dto.setApellido("test");
        dto.setTelefono("+541122334455");
        dto.setClave("clave.secreta#2");
        dto.setCorreo("test@mail.com");
        return dto;
    }

    private Usuario getUsuario() {
        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId(1L);
        usuarioEsperado.setNombre("test");
        usuarioEsperado.setApellido("test");
        usuarioEsperado.setTelefono("+541122334455");
        usuarioEsperado.setClave("clave.secreta#2");
        usuarioEsperado.setCorreo("test@mail.com");
        usuarioEsperado.setRol(new Rol(1L, "USUARIO"));
        return usuarioEsperado;
    }
}