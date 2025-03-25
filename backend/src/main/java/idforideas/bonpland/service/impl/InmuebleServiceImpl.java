package idforideas.bonpland.service.impl;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.exception.InmuebleNoEncontradoException;
import idforideas.bonpland.mapper.impl.InmuebleMapper;
import idforideas.bonpland.repository.InmuebleRepository;
import idforideas.bonpland.service.InmuebleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Figueroa Mauro
 */
@Service
@AllArgsConstructor
public class InmuebleServiceImpl implements InmuebleService {
    private final InmuebleRepository repository;

    private final InmuebleMapper mapper;

    @Override
    public Inmueble guardarInmueble(InmuebleDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Inmueble no puede ser nulo");
        }
        Inmueble inmueble = mapper.map(dto);
        inmueble.setId(null);
        return repository.save(inmueble);
    }

    @Override
    public Page<Inmueble> listarInmuebles() {
        return null;
    }

    @Override
    public Inmueble buscarInmueblePorId(Long id) {
        Optional<Inmueble> inmueble = repository.findById(id);
        return inmueble.orElseThrow(()-> new InmuebleNoEncontradoException("Inmueble no encontrado"));
    }
    @Override
    public Inmueble actualizarInmueble(InmuebleDTO dto) {
        return null;
    }

    @Override
    public int eliminarInmueble(Long id) {
        return 0;
    }
}
