
package idforideas.bonpland.mapper;

import idforideas.bonpland.dto.UsuarioDTO;
import idforideas.bonpland.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Figueroa Mauro
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    Usuario dtoAEntidad(UsuarioDTO usuarioDTO);
    UsuarioDTO entidadADto(Usuario usuario);
}
