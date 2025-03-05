
package idforideas.bonpland.repository;

import idforideas.bonpland.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Martina
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
