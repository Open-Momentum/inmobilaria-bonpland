package idforideas.bonpland.service;

import idforideas.bonpland.dto.UsuarioDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.CorreoExistenteException;
import idforideas.bonpland.exception.RolNoEncontradoException;
import idforideas.bonpland.exception.UsuarioNotFoundException;
import idforideas.bonpland.mapper.impl.UsuarioMapperImpl;
import idforideas.bonpland.repository.RolRepository;
import idforideas.bonpland.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.*;

import idforideas.bonpland.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * @author Figueroa Mauro
 */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    private UsuarioDTO dto;
    private Usuario usuario;
    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @Spy
    UsuarioMapperImpl usuarioMapper;

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
        assertThrowsWithMessage(CorreoExistenteException.class, executable,"El correo ya existe");

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
        assertThrowsWithMessage(RolNoEncontradoException.class, executable, "Rol no encontrado");

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void deberiaLanzarExcepcionBuscandoUsuarioPorId_cuandoNoExiste() {
        //GIVEN
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN
        Executable executable = () -> usuarioService.buscarUsuarioPorId(1L);

        //THEN
       assertThrowsWithMessage(UsuarioNotFoundException.class, executable,"Usuario no encontrado");

        verify(usuarioRepository).findById(any());
    }

    @Test
    void deberiaActualizarUsuario() {
        //GIVEN
        dto.setNombre("nuevo nombre");
        dto.setId(1L);

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("nuevo nombre");
        usuarioActualizado.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuarioActualizado);

        //WHEN
        Usuario result = usuarioService.actualizarUsuario(dto);

        //THEN
        assertEquals(1L, usuario.getId());
        assertEquals("nuevo nombre", result.getNombre());

        verify(usuarioRepository).save(any(Usuario.class));
        verify(usuarioRepository).findById(anyLong());
    }

    @Test
    void deberiaLanzarExcepcion_cuandoElDtoTieneIdNulo() {
        //WHEN
        Executable executable = () -> usuarioService.actualizarUsuario(dto);

        //THEN
        assertThrowsWithMessage(IllegalArgumentException.class, executable, "El id no puede ser nulo para actualizar el usuario");

        verify(usuarioRepository, never()).save(any(Usuario.class));
        verify(usuarioRepository, never()).findById(anyLong());
    }

    private <T extends Exception> void assertThrowsWithMessage(Class<T> tClass, Executable executable, String message) {
        Exception e = assertThrowsExactly(tClass, executable);
        assertEquals(message, e.getMessage());
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