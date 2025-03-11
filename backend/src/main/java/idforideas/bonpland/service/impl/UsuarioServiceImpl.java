package idforideas.bonpland.service.impl;

import idforideas.bonpland.dto.UsuarioDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.CorreoExistenteException;
import idforideas.bonpland.exception.RolNoEncontradoException;
import idforideas.bonpland.exception.UsuarioNotFoundException;
import idforideas.bonpland.mapper.UsuarioMapper;
import idforideas.bonpland.mapper.impl.UsuarioMapperImpl;
import idforideas.bonpland.repository.RolRepository;
import idforideas.bonpland.repository.UsuarioRepository;
import idforideas.bonpland.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 *
 * @author Figueroa Mauro
 */
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;

    private final UsuarioMapper mapper = new UsuarioMapperImpl();

    @Override
    public Usuario guardarUsuario(UsuarioDTO dto) {
        validarCorreo(dto.getCorreo());
        Rol rolEncontrado = validarRol("USUARIO");

        Usuario usuario = mapper.dtoAEntidad(dto);
        usuario.setRol(rolEncontrado);
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return validarUsuarioBuscado(id);
    }

    public Usuario actualizarUsuario(UsuarioDTO dto) {
        validarIdNulo(dto);
        Usuario usuario = mapper.dtoAEntidad(dto);
        if (usuarioRepository.findById(dto.getId()).isPresent()) {

        return usuarioRepository.save(usuario);
        }
        return null;
    }

    private static void validarIdNulo(UsuarioDTO dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("El id no puede ser nulo para actualizar el usuario");
        }
    }

    private  Usuario validarUsuarioBuscado(Long id) {
        return usuarioRepository.findById(id)
                       .orElseThrow(()-> new UsuarioNotFoundException("Usuario no encontrado"));
    }

    private Rol validarRol(String nombre) {
        return rolRepository.findByNombre(nombre)
                       .orElseThrow(() -> new RolNoEncontradoException("Rol no encontrado"));
    }

    private  void validarCorreo(String correo) {
        Optional<Usuario> correoEncontrado = usuarioRepository.findByCorreo(correo);
        if (correoEncontrado.isPresent()) {
            throw new CorreoExistenteException("El correo ya existe");
        }
    }

}
