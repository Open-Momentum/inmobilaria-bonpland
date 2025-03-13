package idforideas.bonpland.repository;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

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
        usuarioRepository.darBaja(10L);

        //THEN --> ver dataset
    }

}