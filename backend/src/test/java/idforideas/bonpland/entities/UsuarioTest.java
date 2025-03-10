package idforideas.bonpland.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 *
 * @author Figueroa Mauro
 */
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    private Usuario usuarioValido;
    private static Validator validator;
    private Set<ConstraintViolation<Usuario>> errores;

    @BeforeEach
    void init() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        usuarioValido = new Usuario();
        usuarioValido.setNombre("test");
        usuarioValido.setApellido("test");
        usuarioValido.setTelefono("+541122334455");
        usuarioValido.setClave("clave.secreta#2");
        usuarioValido.setCorreo("test@mail.com");
        usuarioValido.setRol(new Rol());
        errores = null;
    }

    @Test
    void DeberiaValidarUsuario_cuandoAtributosSonValidos(){
        //WHEN
        errores = validator.validate(usuarioValido);

        //THEN
        assertTrue(errores.isEmpty());
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
    @ValueSource(strings = {"AAAAAAA", "nombreLargoDeMasDe20c"})
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
        assertViolaciones("La clave no debe ser nula ni estar vacia");
        assertViolaciones("El correo no debe ser nulo ni estar vacio");
        assertViolaciones("El telefono no debe ser nulo ni estar vacio");
    }

    @ParameterizedTest
    @ValueSource(strings = {"|@#John", "jo\\@#~½¬{", "..-", ".john","john  doe", " john doe "})
    void deberiaValidarCampo_cuandoTieneCaracteresEspeciales(String input) {
        //WHEN
        validarCampo(input, "setNombre");
        validarCampo(input, "setApellido");

        //THEN
        assertViolaciones("El nombre no puede contener caracteres especiales");
        assertViolaciones("El apellido no puede contener caracteres especiales");
    }

    @ParameterizedTest
    @ValueSource(strings = {"clave#con?si¿gnos!","testExample=","{clavenueva}"})
    void deberiaValidarClave_cuandoTieneCaracteresInvalidos(String input){
        //WHEN
        validarCampo(input, "setClave");

        //THEN
        assertViolaciones("La clave solo acepta letras, numeros y los siguientes caracteres especiales (.-_@#~&)");
    }

    @ParameterizedTest
    @ValueSource(strings = {"mail@m.com","correo.com", "ejemplo@correo.com.ar.ar.ar.ar"})
    void deberiaValidarCorreo_cuandoTieneFormatoInvalido(String input){
        //WHEN
        validarCampo(input, "setCorreo");

        //THEN
        assertViolaciones("Formato de correo invalido");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1122334455","15-45336677","011-35671817","+54-01122334455"})
    void deberiaValidarTelefono_cuandoTieneFormatoInvalido(String input){
        //WHEN
        validarCampo(input, "setTelefono");

        //THEN
        assertViolaciones("Formato de telefono invalido");
    }


    private void validarCampo(String input, String setter) {
        try {
            Method method = Usuario.class.getMethod(setter, String.class);
            method.invoke(usuarioValido, input);

            errores = validator.validate(usuarioValido);
        } catch (NoSuchMethodException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void assertViolaciones(String expectedMessage) {
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(v -> v.getMessage().equals(expectedMessage)));
    }
}
