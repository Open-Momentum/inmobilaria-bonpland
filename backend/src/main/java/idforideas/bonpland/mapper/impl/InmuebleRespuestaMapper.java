package idforideas.bonpland.mapper.impl;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.dto.inmuebles.InmuebleResponseDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.mapper.MapperSimple;
import org.springframework.stereotype.Component;

@Component
public class InmuebleRespuestaMapper implements MapperSimple<InmuebleResponseDTO, Inmueble> {
    @Override
    public InmuebleResponseDTO map(Inmueble entidad) {
        return new InmuebleResponseDTO(entidad.getId(),
                entidad.getDescripcion(),
                entidad.getDireccion(),
                entidad.getCodigoPostal(),
                entidad.getCantAmbientes(),
                entidad.getCantDormi(),
                entidad.getCantCochera(),
                entidad.getMetrosCuadrados(),
                entidad.getTipoPropiedad(),
                entidad.getCodigo());
    }
}
