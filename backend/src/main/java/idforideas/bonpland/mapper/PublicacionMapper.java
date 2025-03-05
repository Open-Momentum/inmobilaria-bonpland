package idforideas.bonpland.mapper;

import idforideas.bonpland.dto.PublicacionDTO;
import idforideas.bonpland.entities.Publicacion;
import org.mapstruct.Mapper;


/**
 *
 * @author Martina
 */
@Mapper(componentModel = "spring")
public interface PublicacionMapper {
        //source: como ingresa desde afuera
        //target: como se llama el atributo internamente
    //@Mapping(source="id", target="id")
    //SOLO PONER MAPPING CUANDO LOS CAMPOS DEL DTO Y ENTIDAD NO COINCIDEN EN NOMBRE
    Publicacion dtoAentidad (PublicacionDTO dto);
     
    
    
    
}
