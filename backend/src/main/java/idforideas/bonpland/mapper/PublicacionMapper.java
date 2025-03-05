package idforideas.bonpland.mapper;

import idforideas.bonpland.dto.PublicacionDTO;
import idforideas.bonpland.entities.Publicacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


/**
 *
 * @author Martina
 */
@Mapper(componentModel = "spring")
public interface PublicacionMapper {
    PublicacionMapper INSTANCE = Mappers.getMapper(PublicacionMapper.class);
        //target: como ingresa desde afuera
        //source: como se llama el atributo internamente
    @Mapping(source="inmueble", target="idInmueble")
    @Mapping(source="usuario", target="idUsuario")
    //SOLO PONER MAPPING CUANDO LOS CAMPOS DEL DTO Y ENTIDAD NO COINCIDEN EN NOMBRE
    Publicacion dtoAentidad (PublicacionDTO dto);
     
    
    
    
}
