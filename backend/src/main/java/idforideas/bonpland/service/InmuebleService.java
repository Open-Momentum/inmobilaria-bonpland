package idforideas.bonpland.service;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import org.springframework.data.domain.Page;

public interface InmuebleService {

    Inmueble guardarInmueble(InmuebleDTO dto);

    Page<Inmueble> listarInmuebles();

    Inmueble buscarInmueblePorId(Long id);

    Inmueble actualizarInmueble(InmuebleDTO dto);

    void eliminarInmueble(Long id);
}
