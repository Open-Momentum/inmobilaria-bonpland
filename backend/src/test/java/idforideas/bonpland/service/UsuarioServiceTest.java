package idforideas.bonpland.service;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.*;
import idforideas.bonpland.mapper.impl.UsuarioCompletoMapper;
import idforideas.bonpland.repository.RolRepository;
import idforideas.bonpland.repository.UsuarioRepository;

import static idforideas.bonpland.utils.TestUtil.getUsuario;
import static idforideas.bonpland.utils.TestUtil.getUsuarioDTO;
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
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * @author Figueroa Mauro
 */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    private UsuarioCompletoDTO dto;
    private Usuario usuario;
    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;
    @Spy
    UsuarioCompletoMapper usuarioMapper;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

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
    void deberiaRetornarUsuarioPorId() {
        //GIVEN
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(getUsuario()));

        //WHEN
        Usuario usuarioEncontrado = usuarioService.buscarUsuarioPorId(1L);

        //THEN
        assertNotNull(usuarioEncontrado);

        verify(usuarioRepository).findById(any());
    }

    @Test
    void deberiaLanzarExcepcionBuscandoUsuarioPorId_cuandoNoExiste() {
        //GIVEN
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN
        Executable executable = () -> usuarioService.buscarUsuarioPorId(1L);

        //THEN
       assertThrowsWithMessage(UsuarioNoEncontradoException.class, executable,"Usuario no encontrado");

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
        assertThrowsWithMessage(IdInexistenteException.class, executable, "El id no puede ser nulo para realizar esta acción");

        verify(usuarioRepository, never()).save(any(Usuario.class));
        verify(usuarioRepository, never()).findById(anyLong());
    }

@Test
void deberiaLanzarExceptionConCorreoDiferente_cuandoSeActualiza(){
    //GIVEN
    dto.setId(1L);
    dto.setCorreo("nuevo_correo@gmail.com");
    when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(getUsuario()));

    //WHEN
    Executable executable = () -> usuarioService.actualizarUsuario(dto);

    //THEN
    assertThrowsWithMessage(CambioCorreoException.class,executable,"No puedes cambiar el correo de tu cuenta");
}

    @Test
    void deberiaListarUsuarios_conPaginacion(){
        //GIVEN
        Pageable pageable = PageRequest.of(0, 10);
        List<Usuario> usuarioList = List.of(getUsuario(), getUsuario(), getUsuario());
        Page<Usuario> usuarioPage = new PageImpl<>(usuarioList, pageable, usuarioList.size());

        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(usuarioPage);

        //WHEN
        Page<Usuario> result = usuarioService.listarUsuarios(pageable);

        //THEN
        assertEquals(3, result.getTotalElements());
        assertEquals(1,result.getTotalPages());

        verify(usuarioRepository).findAll(any(Pageable.class));
    }

    @Test
    void deberiaDarBajaUsuario(){
        //GIVEN
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.bajaLogica(1L)).thenAnswer(invocation -> {
            usuario.setActivo(false);
            return 1;
        } );

        //WHEN
        int filasAfectadas = usuarioService.bajaLogicaUsuario(1L);

        //THEN
        assertFalse(usuario.getActivo());
        assertEquals(1, filasAfectadas);

        verify(usuarioRepository).bajaLogica(anyLong());
    }

    private <T extends Exception> void assertThrowsWithMessage(Class<T> tClass, Executable executable, String message) {
        Exception e = assertThrowsExactly(tClass, executable);
        assertEquals(message, e.getMessage());
    }


}