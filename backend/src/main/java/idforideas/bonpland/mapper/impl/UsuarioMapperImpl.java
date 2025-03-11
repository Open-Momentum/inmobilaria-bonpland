package idforideas.bonpland.mapper.impl;

import idforideas.bonpland.dto.UsuarioDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.UsuarioMapper;

public class UsuarioMapperImpl implements UsuarioMapper {
    @Override
    public Usuario dtoAEntidad(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        if (dto.getId() != null) {
            usuario.setId(dto.getId());
        }
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setClave(dto.getClave());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        return usuario;
    }
}
