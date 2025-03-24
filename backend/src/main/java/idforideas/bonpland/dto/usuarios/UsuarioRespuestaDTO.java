package idforideas.bonpland.dto.usuarios;

import idforideas.bonpland.dto.DTO;
/**
 *
 * @author Figueroa Mauro
 */
public record UsuarioRespuestaDTO(Long id, String nombre, String apellido, String correo) implements DTO {
}
