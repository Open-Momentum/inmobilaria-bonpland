package idforideas.bonpland.service.impl;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.CorreoExistenteException;
import idforideas.bonpland.exception.RolNoEncontradoException;
import idforideas.bonpland.exception.UsuarioNoEncontradoException;
import idforideas.bonpland.exception.IdInexistenteException;
import idforideas.bonpland.mapper.impl.UsuarioCompletoMapper;
import idforideas.bonpland.repository.RolRepository;
import idforideas.bonpland.repository.UsuarioRepository;
import idforideas.bonpland.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 * @author Figueroa Mauro
 */

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioCompletoMapper mapper;

    @Override
    @Transactional
    public Usuario guardarUsuario(UsuarioCompletoDTO dto) {
        validarCorreo(dto.getCorreo());

        Usuario usuario = mapper.map(dto);
        usuario.setId(null);
        asignarRol(usuario,"usuario");
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        validarIdNulo(id);
        return validarUsuarioBuscado(id);
    }

    @Override
    @Transactional
    public Usuario actualizarUsuario(UsuarioCompletoDTO dto) {
        validarIdNulo(dto.getId());
        Usuario usuarioBuscado = validarUsuarioBuscado(dto.getId());
        if (!usuarioBuscado.getCorreo().equals(dto.getCorreo())) {
        validarCorreo(dto.getCorreo());
        }

        Usuario usuario = mapper.map(dto);
        usuario.setRol(usuarioBuscado.getRol());
        return usuarioRepository.save(usuario);
    }


    @Override
    public Page<Usuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public int bajaLogicaUsuario(Long id) {
        validarIdNulo(id);
        validarUsuarioBuscado(id);

        return usuarioRepository.bajaLogica(id);
    }

    private void asignarRol(Usuario usuario, String nombreRol) {
        Rol rolEncontrado = validarRol(nombreRol.toUpperCase());
        usuario.setRol(rolEncontrado);
    }


    private static void validarIdNulo(Long id) {
        if (id == null) {
            throw new IdInexistenteException("El id no puede ser nulo para realizar esta acción");
        }
    }

    private  Usuario validarUsuarioBuscado(Long id) {
        return usuarioRepository.findById(id)
                       .orElseThrow(()-> new UsuarioNoEncontradoException("Usuario no encontrado"));
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
