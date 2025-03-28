package idforideas.bonpland.repository;

import idforideas.bonpland.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 * @author Figueroa Mauro
 */
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);
}
