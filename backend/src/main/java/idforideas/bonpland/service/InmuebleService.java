package idforideas.bonpland.service;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InmuebleService {

    Inmueble guardarInmueble(InmuebleDTO dto);

    Page<Inmueble> listarInmuebles(Pageable pageable);

    Inmueble buscarInmueblePorId(Long id);

    Inmueble actualizarInmueble(InmuebleDTO dto);

    void eliminarInmueble(Long id);
}
