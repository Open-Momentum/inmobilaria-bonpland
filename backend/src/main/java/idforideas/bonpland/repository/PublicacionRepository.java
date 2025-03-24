
package idforideas.bonpland.repository;

import idforideas.bonpland.entities.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Martina
 */
@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    
}
