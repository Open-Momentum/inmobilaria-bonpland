
package idforideas.bonpland.repository;

import idforideas.bonpland.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Figueroa Mauro
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    @Modifying
    @Query(value = "UPDATE Usuario u SET u.activo=false WHERE u.id=:id")
    int darBaja(Long id);
}
