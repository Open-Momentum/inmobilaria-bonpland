package idforideas.bonpland.service;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.enumerations.TipoPropiedad;
import idforideas.bonpland.repository.InmuebleRepository;
import idforideas.bonpland.service.impl.InmuebleServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Figueroa Mauro
 */
@ExtendWith(MockitoExtension.class)
class InmuebleServiceImplTest {

    @Mock
    private InmuebleRepository inmuebleRepository;

    @InjectMocks
    InmuebleServiceImpl inmuebleService;

}