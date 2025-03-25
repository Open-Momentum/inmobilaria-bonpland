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
    private final InmuebleRepository repository;

    @Override
    public Inmueble guardarInmueble(InmuebleDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Inmueble no puede ser nulo");
        }
        Inmueble inmueble = new Inmueble(null, dto.descripcion(), dto.codigo(), dto.direccion(), dto.codigoPostal(),
                dto.cantAmbientes(), dto.cantDormi(), dto.cantBanos(), dto.cantCochera(), dto.metrosCuadrados(), dto.tipoPropiedad(), dto.fotos(), null);
        return repository.save(inmueble);
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
