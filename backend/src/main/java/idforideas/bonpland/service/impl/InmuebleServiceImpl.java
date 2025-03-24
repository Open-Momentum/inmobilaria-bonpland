package idforideas.bonpland.service.impl;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.repository.InmuebleRepository;
import idforideas.bonpland.service.InmuebleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Figueroa Mauro
 */
@Service
@AllArgsConstructor
public class InmuebleServiceImpl implements InmuebleService {
    private InmuebleRepository repository;

    @Override
    public Inmueble guardarInmueble(InmuebleDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Inmueble no puede ser nulo");
        }
        return repository.save(new Inmueble());
    }

    @Override
    public Page<Inmueble> listarInmuebles() {
        return null;
    }

    @Override
    public Inmueble buscarInmueblePorId(Long id) {
        return null;
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
