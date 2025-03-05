
package idforideas.bonpland.mapper.impl;

import idforideas.bonpland.dto.PublicacionDTO;
import idforideas.bonpland.entities.Publicacion;
import idforideas.bonpland.mapper.PublicacionMapper;

/**
 *
 * @author Martina
 */
public class PublicacionMapperImpl implements PublicacionMapper{

    @Override
    public Publicacion dtoAentidad(PublicacionDTO dto) {
        Publicacion p=new Publicacion();
        p.setTitulo(dto.getTitulo());
        p.setDescripcion(dto.getDescripcion());
        //metodo para obtener la fecha actual
        p.setPrecio(dto.getPrecio());
        p.setEstado(dto.getEstado());
        return p;
    }
    
}
