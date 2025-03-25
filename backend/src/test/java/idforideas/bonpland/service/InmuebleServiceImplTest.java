package idforideas.bonpland.service;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.enumerations.TipoPropiedad;
import idforideas.bonpland.mapper.impl.InmuebleMapper;
import idforideas.bonpland.repository.InmuebleRepository;
import idforideas.bonpland.service.impl.InmuebleServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

/**
 * @author Figueroa Mauro
 */
@ExtendWith(MockitoExtension.class)
class InmuebleServiceImplTest {

    @Mock
    private InmuebleRepository inmuebleRepository;

    @InjectMocks
    InmuebleServiceImpl inmuebleService;
    @Spy
    InmuebleMapper mapper;

    @Test
    void deberiaGuardarInmueble(){
        //GIVEN
        InmuebleDTO dto = new InmuebleDTO(1L, "descripcion", 1000, "Direccion", 1999,
                3, 4, 1, 1, 100, TipoPropiedad.CASA);

        //WHEN
        inmuebleService.guardarInmueble(dto);

        //THEN
        verify(inmuebleRepository).save(any(Inmueble.class));
    }

    @Test
    void deberiaLanzarException_cuandoLosDatosSonNulos() {
        //WHEN
        Executable executable = () -> inmuebleService.guardarInmueble(null);

        //THEN
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Inmueble no puede ser nulo", e.getMessage());

        verify(inmuebleRepository,never()).save(any());
    }

    @Test
    void deberiaSetearElIdEnNuloAntesDeGuardar() {
        //GIVEN
        InmuebleDTO dto = new InmuebleDTO(1L, "descripcion", 1000, "Direccion", 1999,
                3, 4, 1, 1, 100, TipoPropiedad.CASA);

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
    void deberiaBuscarPorId(){
        //GIVEN
        Long id = 1L;
        when(inmuebleRepository.findById(id)).thenReturn(Optional.of(new Inmueble()));

        //WHEN
        Inmueble inmueble = inmuebleService.buscarInmueblePorId(id);

        //THEN
        assertNotNull(inmueble);

        verify(inmuebleRepository).findById(1L);
    }

}