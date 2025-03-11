
package idforideas.bonpland.service;

import idforideas.bonpland.dto.UsuarioDTO;
import idforideas.bonpland.entities.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 * @author Figueroa Mauro
 */
@Service
public interface UsuarioService {

    Usuario guardarUsuario(UsuarioDTO dto);

    Usuario buscarUsuarioPorId(Long id);
}
