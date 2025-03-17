
package idforideas.bonpland.dto.usuarios;

import idforideas.bonpland.dto.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Figueroa Mauro
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCompletoDTO implements DTO {
    private static final String CORREO_PATTERN = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9]{2,}(\\.[a-zA-Z0-9-]{2,}){1,4}$";
    private static final String NOMBRE_PATTERN = "^[a-zA-Z]+( [a-zA-Z]+)*$";
    private static final String CLAVE_PATTERN = "^[A-Za-z0-9._\\-@#~&]+$";
    private static final String TELEFONO_PATTERN = "^\\+\\d{1,3}\\d{6,10}$";

    private Long id;

    @Schema( example = "John")
    @NotBlank(message = "El nombre no debe ser nulo ni estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = NOMBRE_PATTERN, message = "El nombre no puede contener caracteres especiales")
    private String nombre;

    @Schema(example = "Doe")
    @NotBlank(message = "El apellido no debe ser nulo ni estar vacío")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Pattern(regexp = NOMBRE_PATTERN, message = "El apellido no puede contener caracteres especiales")
    private String apellido;

    @Schema(example = "clave.1234")
    @NotBlank(message = "La clave no debe ser nula ni estar vacía")
    @Size(min = 8, max = 20, message = "La clave debe tener entre 8 y 20 caracteres")
    @Pattern(regexp = CLAVE_PATTERN, message = "La clave solo acepta letras, números y los siguientes caracteres especiales (.-_@#~&)")
    private String clave;

    @Schema(example = "johndoe@mail.com")
    @NotBlank(message = "El correo no debe ser nulo ni estar vacío")
    @Size(min = 4, max = 200, message = "El correo debe tener menos de 200 caracteres")
    @Pattern(regexp = CORREO_PATTERN, message = "Formato de correo inválido")
    private String correo;

    @Schema(example = "+541122334455")
    @NotBlank(message = "El teléfono no debe ser nulo ni estar vacío")
    @Pattern(regexp = TELEFONO_PATTERN, message = "Formato de teléfono inválido")
    private String telefono;

}
