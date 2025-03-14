package idforideas.bonpland.mapper.impl;

import idforideas.bonpland.dto.usuarios.UsuarioRegisterResponseDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.MapperSimple;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRespuestaMapper implements MapperSimple<UsuarioRegisterResponseDTO,Usuario> {

    @Override
    public UsuarioRegisterResponseDTO map(Usuario entidad) {
        return new UsuarioRegisterResponseDTO(
                entidad.getId(), entidad.getNombre(), entidad.getApellido(), entidad.getCorreo());
    }


}
