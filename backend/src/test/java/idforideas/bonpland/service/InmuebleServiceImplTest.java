package idforideas.bonpland.service;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.enumerations.TipoPropiedad;
import idforideas.bonpland.exception.InmuebleNoEncontradoException;
import idforideas.bonpland.mapper.impl.InmuebleMapper;
import idforideas.bonpland.repository.InmuebleRepository;
import idforideas.bonpland.repository.UsuarioRepository;
import idforideas.bonpland.service.impl.InmuebleServiceImpl;

import static idforideas.bonpland.utils.TestUtil.getInmuebleDto;
import static idforideas.bonpland.utils.TestUtil.getUsuario;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Figueroa Mauro
 */
@ExtendWith(MockitoExtension.class)
class InmuebleServiceImplTest {
    private InmuebleDTO dto;
    private Usuario usuario;
    @Mock
    private InmuebleRepository inmuebleRepository;
    @InjectMocks
    private InmuebleServiceImpl inmuebleService;
    @Spy
    private InmuebleMapper mapper;
    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void init() {
        dto = getInmuebleDto();
        usuario = getUsuario();
        Authentication auth = new UsernamePasswordAuthenticationToken(usuario.getCorreo(), null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void deberiaGuardarInmueble_conUsuarioLogueado() {
        //GIVEN
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));

        //WHEN
        inmuebleService.guardarInmueble(dto);

        //THEN
        verify(inmuebleRepository).save(any(Inmueble.class));
    }


    @Test
    void deberiaLanzarException_cuandoLosDatosSonNulos() {
        //GIVEN
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));

        //WHEN
        Executable executable = () -> inmuebleService.guardarInmueble(null);

        //THEN
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Inmueble no puede ser nulo", e.getMessage());

        verify(inmuebleRepository, never()).save(any());
    }

    @Test
    void deberiaSetearElIdEnNuloAntesDeGuardar() {
        //GIVEN
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));

        //WHEN
        inmuebleService.guardarInmueble(dto);
        ArgumentCaptor<Inmueble> captor = ArgumentCaptor.forClass(Inmueble.class);
        verify(inmuebleRepository).save(captor.capture());

        //THEN
        Inmueble capturado = captor.getValue();
        assertNull(capturado.getId());
        assertEquals("descripcion", capturado.getDescripcion());

        verify(inmuebleRepository).save(any(Inmueble.class));
    }

    @Test
    void deberiaListarInmuebles(){
        //GIVEN
        List<Inmueble> list = List.of(new Inmueble(), new Inmueble(), new Inmueble(), new Inmueble());
        Pageable pageable = PageRequest.of(0, 10);
        when(inmuebleRepository.findAll(pageable)).thenReturn(new PageImpl<>(list, pageable, list.size()));

        //WHEN
        Page<Inmueble> inmuebles = inmuebleService.listarInmuebles(pageable);

        //THEN
        assertEquals(4, inmuebles.getTotalElements());
        assertEquals(1, inmuebles.getTotalPages());

        verify(inmuebleRepository).findAll(any(Pageable.class));
    }

    @Test
    void deberiaBuscarPorId() {
        //GIVEN
        Long id = 1L;
        when(inmuebleRepository.findById(id)).thenReturn(Optional.of(new Inmueble()));

        //WHEN
        Inmueble inmueble = inmuebleService.buscarInmueblePorId(id);

        //THEN
        assertNotNull(inmueble);

        verify(inmuebleRepository).findById(1L);
    }

    @Test
    void deberiaLanzarException_cuandoNoEncuentraElInmueble() {
        //GIVEN
        Long id = 1L;
        when(inmuebleRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN
        Executable executable = () -> inmuebleService.buscarInmueblePorId(id);

        //THEN
        InmuebleNoEncontradoException e = assertThrows(InmuebleNoEncontradoException.class, executable);
        assertEquals("Inmueble no encontrado", e.getMessage());

        verify(inmuebleRepository, never()).save(any());
    }


    @Test
    void deberiaActualizarInmueble_conUsuarioLogueado() {
        //GIVEN
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.ofNullable(usuario));
        when(inmuebleRepository.findById(1L)).thenReturn(Optional.of(new Inmueble()));

        //WHEN
        inmuebleService.actualizarInmueble(dto);


        //THEN
        verify(inmuebleRepository).save(any(Inmueble.class));
        verify(inmuebleRepository).findById(1L);
    }

    @Test
    void deberiaEliminarInmueble_conUsuarioLogueado() {
        //GIVEN
        Inmueble inmueble = new Inmueble(1L, "test", "test",
                1899, 1, 2, 3, 4,
                100, TipoPropiedad.CASA, null, usuario);
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.ofNullable(usuario));
        when(inmuebleRepository.findById(1L)).thenReturn(Optional.of(inmueble));

        //WHEN
        inmuebleService.eliminarInmueble(1L);

        //THEN
        verify(inmuebleRepository).deleteById(1L);
    }

}
