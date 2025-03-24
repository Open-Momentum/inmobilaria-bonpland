package idforideas.bonpland.mapper.impl;

import idforideas.bonpland.dto.usuarios.UsuarioRespuestaDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.MapperSimple;
import org.springframework.stereotype.Component;

/**
 *
 * @author Figueroa Mauro
 */
@Component
public class UsuarioRespuestaMapper implements MapperSimple<UsuarioRespuestaDTO,Usuario> {

    @Override
    public UsuarioRespuestaDTO map(Usuario entidad) {
        return new UsuarioRespuestaDTO(
                entidad.getId(), entidad.getNombre(), entidad.getApellido(), entidad.getCorreo());
    }


}
