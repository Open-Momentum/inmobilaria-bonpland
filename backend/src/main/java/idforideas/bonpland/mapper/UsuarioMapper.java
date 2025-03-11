
package idforideas.bonpland.mapper;

import idforideas.bonpland.dto.UsuarioDTO;
import idforideas.bonpland.entities.Usuario;

/**
 *
 * @author Figueroa Mauro
 */
public interface UsuarioMapper {

    Usuario dtoAEntidad(UsuarioDTO usuarioDTO);
}
