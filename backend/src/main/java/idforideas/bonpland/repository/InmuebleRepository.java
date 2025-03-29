package idforideas.bonpland.repository;

import idforideas.bonpland.entities.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Figueroa Mauro
 */
@Repository
public interface InmuebleRepository extends JpaRepository<Inmueble,Long> {
}
