package idforideas.bonpland.utils;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;

public class TestUtil {

    public static UsuarioCompletoDTO getUsuarioDTO() {
        UsuarioCompletoDTO dto = new UsuarioCompletoDTO();
        dto.setNombre("test");
        dto.setApellido("test");
        dto.setTelefono("+541122334455");
        dto.setClave("clave.secreta#2");
        dto.setCorreo("test@mail.com");
        return dto;
    }

    public static Usuario getUsuario() {
        Usuario usuarioValido = new Usuario();
        usuarioValido.setId(1L);
        usuarioValido.setNombre("test");
        usuarioValido.setApellido("test");
        usuarioValido.setTelefono("+541122334455");
        usuarioValido.setClave("clave.secreta#2");
        usuarioValido.setCorreo("test@mail.com");
        usuarioValido.setRol(new Rol(1L, "USUARIO"));
        return usuarioValido;
    }
}
