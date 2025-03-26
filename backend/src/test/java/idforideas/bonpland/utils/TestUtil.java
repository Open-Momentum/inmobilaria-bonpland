package idforideas.bonpland.utils;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.enumerations.TipoPropiedad;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Figueroa Mauro
 */
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

    public static List<Usuario> getUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Usuario actual = getUsuario();
            actual.setId((long) i + 1);
            lista.add(actual);
        }
        return lista;
    }

    public static @NotNull InmuebleDTO getInmuebleDto() {
        return new InmuebleDTO(1L, "descripcion", "direccion", 1999,
                3, 4, 1, 1, 100, TipoPropiedad.CASA);
    }
}
