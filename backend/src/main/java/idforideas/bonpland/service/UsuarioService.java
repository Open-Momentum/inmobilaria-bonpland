
package idforideas.bonpland.service;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Figueroa Mauro
 */
@Service
public interface UsuarioService {

    Usuario guardarUsuario(UsuarioCompletoDTO dto);

    Usuario buscarUsuarioPorId(Long id);

    Usuario actualizarUsuario(UsuarioCompletoDTO dto);

    int bajaLogicaUsuario(Long id);

    Page<Usuario> listarUsuarios(Pageable pageable);
}
