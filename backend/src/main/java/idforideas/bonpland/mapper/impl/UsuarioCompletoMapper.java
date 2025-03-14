package idforideas.bonpland.mapper.impl;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.Mapper;

public class UsuarioCompletoMapper implements Mapper<Usuario, UsuarioCompletoDTO> {


    @Override
    public Usuario aEntidad(UsuarioCompletoDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setClave(dto.getClave());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        return usuario;
    }

    @Override
    public UsuarioCompletoDTO aDTO(Usuario entidad) {
        return null;
    }
}
