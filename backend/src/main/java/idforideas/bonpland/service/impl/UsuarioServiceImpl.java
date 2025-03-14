package idforideas.bonpland.service.impl;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.CorreoExistenteException;
import idforideas.bonpland.exception.RolNoEncontradoException;
import idforideas.bonpland.exception.UsuarioNotFoundException;
import idforideas.bonpland.mapper.Mapper;
import idforideas.bonpland.mapper.impl.UsuarioCompletoMapper;
import idforideas.bonpland.repository.RolRepository;
import idforideas.bonpland.repository.UsuarioRepository;
import idforideas.bonpland.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 *
 * @author Figueroa Mauro
 */

@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioCompletoMapper mapper;

    @Override
    public Usuario guardarUsuario(UsuarioCompletoDTO dto) {
        validarCorreo(dto.getCorreo());
        Rol rolEncontrado = validarRol("USUARIO");

        Usuario usuario = mapper.aEntidad(dto);
        usuario.setRol(rolEncontrado);
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        validarIdNulo(id);
        return validarUsuarioBuscado(id);
    }

    public Usuario actualizarUsuario(UsuarioCompletoDTO dto) {
        validarIdNulo(dto.getId());
        validarUsuarioBuscado(dto.getId());

        Usuario usuario = mapper.aEntidad(dto);
        return usuarioRepository.save(usuario);
    }

    public Page<Usuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public int bajaUsuario(Long id) {
        validarIdNulo(id);
        validarUsuarioBuscado(id);

        return usuarioRepository.darBaja(id);
    }

    private static void validarIdNulo(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo para realizar esta acción");
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
