package idforideas.bonpland.dto.usuarios;

import idforideas.bonpland.dto.DTO;

public record UsuarioRegisterResponseDTO(Long id, String nombre, String apellido, String correo) implements DTO {
}
