package idforideas.bonpland.mapper.impl;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.MapperDoble;
import idforideas.bonpland.mapper.MapperSimple;
import org.springframework.stereotype.Component;

@Component
public class UsuarioCompletoMapper implements MapperSimple<Usuario, UsuarioCompletoDTO> {

    @Override
    public Usuario map(UsuarioCompletoDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setClave(dto.getClave());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        return usuario;
    }

}
