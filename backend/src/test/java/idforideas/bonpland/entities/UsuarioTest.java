package idforideas.bonpland.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    private Usuario usuario;
    private static Validator validator;
    private ValidatorFactory validatorFactory;
    private Set<ConstraintViolation<Usuario>> violaciones;


    @BeforeAll
    static void setup() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }
    }

    @BeforeEach
    void init() {
        usuario = new Usuario();
        usuario.setApellido("test");
        usuario.setTelefono("1122334455");
        usuario.setClave("123123123");
        usuario.setCorreo("test@mail.com");
        usuario.setRol(new Rol());
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "nombreLargoDeMasDeCincuentaCaracteresAAABBBAAABBBAAA"})
    void deberiaValidarCampo_cuandoLongitudEsInvalida(String input) {
        //WHEN
        validarCampo(input,"setNombre");

        //THEN
        assertViolaciones("El nombre debe tener entre 2 y 50 caracteres");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void deberiaValidarCampo_cuandoEsNulo(String input) {
        //WHEN
        validarCampo(input,"setNombre");

        //THEN
        assertViolaciones("El nombre no debe ser nulo ni estar vacio");
    }

    @ParameterizedTest
    @ValueSource(strings = {"|@#John", "jo\\@#~½¬{", "..-", ".john"})
    void deberiaValidarCampo_cuandoTieneCaracteresEspeciales(String input) {
        //WHEN
        validarCampo(input,"setNombre");

        //THEN
        assertFalse(violaciones.isEmpty());
        assertViolaciones("El nombre no puede contener caracteres especiales");
    }

    private void validarCampo(String input, String setter) {
        try {
            Method method = Usuario.class.getMethod(setter, String.class);
            method.invoke(usuario, input);

            violaciones = validator.validate(usuario);
        } catch (NoSuchMethodException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void assertViolaciones(String expectedMessage) {
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().equals(expectedMessage)));
    }
}