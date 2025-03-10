
package idforideas.bonpland.service;

import idforideas.bonpland.dto.UsuarioDTO;
import idforideas.bonpland.entities.Usuario;
import org.springframework.stereotype.Service;

/**
 *
 * @author Figueroa Mauro
 */
@Service
public interface UsuarioService {

    Usuario guardarUsuario(UsuarioDTO dto);
}
