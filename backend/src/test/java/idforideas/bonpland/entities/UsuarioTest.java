package idforideas.bonpland.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    private Usuario usuario;
    private static Validator validator;
    private Set<ConstraintViolation<Usuario>> violaciones;

    @BeforeEach
    void init() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        usuario = new Usuario();
        usuario.setNombre("test");
        usuario.setApellido("test");
        usuario.setTelefono("1122334455");
        usuario.setClave("12a.asd3123123");
        usuario.setCorreo("test@mail.com");
        usuario.setRol(new Rol());
        violaciones = null;
    }


    @ParameterizedTest
    @ValueSource(strings = {"A", "nombreLargoDeMasDeCincuentaCaracteresAAABBBAAABBBAAA"})
    void deberiaValidarCampo_cuandoLongitudEs2a50Caracteres(String input) {
        //WHEN
        validarCampo(input, "setNombre");
        validarCampo(input, "setApellido");

        //THEN
        assertViolaciones("El nombre debe tener entre 2 y 50 caracteres");
        assertViolaciones("El apellido debe tener entre 2 y 50 caracteres");
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "nombreLargoDeMasDeCincuentaCaracteresAAABBBAAABBBAAA"})
    void deberiaValidarCampo_cuandoLongitudEs8a20Caracteres(String input) {
        //WHEN
        validarCampo(input, "setClave");

        //THEN
        assertViolaciones("La clave debe tener entre 8 y 20 caracteres");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void deberiaValidarCampo_cuandoEsNuloOVacio(String input) {
        //WHEN
        validarCampo(input, "setNombre");
        validarCampo(input, "setApellido");
        validarCampo(input, "setClave");
        validarCampo(input, "setCorreo");
        validarCampo(input, "setTelefono");

        //THEN
        assertViolaciones("El nombre no debe ser nulo ni estar vacio");
        assertViolaciones("El apellido no debe ser nulo ni estar vacio");
        assertViolaciones("La clave no debe ser nula");
        assertViolaciones("El correo no debe ser nulo");
        assertViolaciones("El telefono no debe ser nulo");
    }

    @ParameterizedTest
    @ValueSource(strings = {"|@#John", "jo\\@#~½¬{", "..-", ".john"})
    void deberiaValidarCampo_cuandoTieneCaracteresEspeciales(String input) {
        //WHEN
        validarCampo(input, "setNombre");
        validarCampo(input, "setApellido");

        //THEN
        assertViolaciones("El nombre no puede contener caracteres especiales");
        assertViolaciones("El apellido no puede contener caracteres especiales");
    }

    @ParameterizedTest
    @ValueSource(strings = {"clave#con?si¿gnos!"})
    void deberiaValidarClave_cuandoTieneCaracteresInvalidos(String input){
        //WHEN
        validarCampo(input, "setClave");

        //THEN
        assertViolaciones("La clave solo acepta letras, numeros y los siguientes caracteres especiales (.-_@#~&)");
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
