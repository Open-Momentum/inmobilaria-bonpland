package idforideas.bonpland.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO.CLAVE_PATTERN;
import static idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO.CORREO_PATTERN;


/**
 * @author Figueroa Mauro
 */
public record LoginDTO(

        @Schema(example = "johndoe@mail.com")
        @NotBlank(message = "El correo no debe ser nulo ni estar vacío")
        @Size(min = 4, max = 200, message = "El correo debe tener menos de 200 caracteres")
        @Pattern(regexp = CORREO_PATTERN, message = "Formato de correo inválido")
         String correo,
        @Schema(example = "clave.1234")
        @NotBlank(message = "La clave no debe ser nula ni estar vacía")
        @Size(min = 8, max = 20, message = "La clave debe tener entre 8 y 20 caracteres")
        @Pattern(regexp = CLAVE_PATTERN, message = "La clave solo acepta letras, números y los siguientes caracteres especiales (.-_@#~&)")
         String clave
) {
}
