package idforideas.bonpland.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    Usuario usuario;
    static Validator validator;
    ValidatorFactory validatorFactory;


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
    void deberiaValidarNombre_cuandoLongitudEsInvalida(String input) {
        //WHEN
        usuario.setNombre(input);
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);
        //THEN
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().equals("El nombre debe tener entre 2 y 50 caracteres")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void deberiaValidarNombre_cuandoEsNulo(String input) {
        //WHEN
        usuario.setNombre(input);
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);
        //THEN
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().equals("El nombre no debe ser nulo ni estar vacio")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"|@#John", "jo\\@#~½¬{", "..-", ".john"})
    void deberiaValidarNombre_cuandoTieneCaracteresEspeciales(String input){
        //WHEN
        usuario.setNombre(input);
        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);
        //THEN
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().equals("El nombre no puede contener caracteres especiales")));
    }
}