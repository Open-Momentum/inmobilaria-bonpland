package idforideas.bonpland.mapper.impl;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.mapper.MapperDoble;
import idforideas.bonpland.mapper.MapperSimple;
import org.springframework.stereotype.Component;

/**
 * @author Figueroa Mauro
 */
@Component
public class InmuebleMapper implements MapperDoble<Inmueble, InmuebleDTO> {
    @Override
    public Inmueble map(InmuebleDTO dto) {
        return new Inmueble(dto.id(),
                dto.descripcion(),
                dto.codigo(),
                dto.direccion(),
                dto.codigoPostal(),
                dto.cantAmbientes(),
                dto.cantDormi(),
                dto.cantBanos(),
                dto.cantCochera(),
                dto.metrosCuadrados(),
                dto.tipoPropiedad(),
                null,
                null);
    }

    @Override
    public InmuebleDTO map(Inmueble entidad) {
        return new InmuebleDTO(entidad.getId(),
                entidad.getDescripcion(),
                entidad.getCodigo(),
                entidad.getDireccion(),
                entidad.getCodigoPostal(),
                entidad.getCantAmbientes(),
                entidad.getCantDormi(),
                entidad.getCantBanos(),
                entidad.getCantCochera(),
                entidad.getMetrosCuadrados(),
                entidad.getTipoPropiedad());
    }
}
