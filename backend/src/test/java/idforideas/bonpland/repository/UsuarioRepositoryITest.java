package idforideas.bonpland.repository;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import idforideas.bonpland.entities.Usuario;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

/**
 *
 * @author Figueroa Mauro
 */
@ActiveProfiles("test")
@DataJpaTest
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE, schema = "PUBLIC")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DBRider
@DataSet(value = "usuarios.json")
class UsuarioRepositoryITest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @ExpectedDataSet(value = "usuarios-baja.json")
    void deberiaCambiarActivoAFalse_cuandoSeDaDeBaja() {
        //WHEN
        int filasAfectadas =usuarioRepository.darBaja(10L);

        //THEN --> ver Dataset
        assertEquals(1, filasAfectadas);
    }

    @Test
    void deberiaRetornarCero_cuandoSeDaBajaUsuarioInexistente() {
        //WHEN
        int filasAfectadas =usuarioRepository.darBaja(100L);

        //THEN
        assertEquals(0, filasAfectadas);
    }

    @Test
    void deberiaBuscarUsuarioPorCorreo(){
        //WHEN
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByCorreo("test@mail.com");

        //THEN
        assertTrue(usuarioBuscado.isPresent());
        assertEquals("test", usuarioBuscado.get().getNombre());
        assertNotNull(usuarioBuscado.get().getId());
    }

    @Test
    void deberiaRetornarOptionalVacio_cuandoNoExisteUsuarioConEseCorreo(){
        //WHEN
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByCorreo("ejemplo@mail.com");

        //THEN
        assertFalse(usuarioBuscado.isPresent());
    }
}