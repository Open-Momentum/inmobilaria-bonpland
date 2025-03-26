package idforideas.bonpland.service.impl;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.InmuebleNoEncontradoException;
import idforideas.bonpland.exception.UsuarioNoEncontradoException;
import idforideas.bonpland.exception.UsuarioSinPermisoException;
import idforideas.bonpland.mapper.impl.InmuebleMapper;
import idforideas.bonpland.repository.InmuebleRepository;
import idforideas.bonpland.repository.UsuarioRepository;
import idforideas.bonpland.security.CustomUserDetailService;
import idforideas.bonpland.service.InmuebleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Figueroa Mauro
 */
@Service
@AllArgsConstructor
public class InmuebleServiceImpl implements InmuebleService {
    private final InmuebleRepository inmuebleRepository;
    private final UsuarioRepository usuarioRepository;

    private final InmuebleMapper mapper;
    private CustomUserDetailService customUserDetailService;

    @Override
    @Transactional
    public Inmueble guardarInmueble(InmuebleDTO dto) {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        if (dto == null) {
            throw new IllegalArgumentException("Inmueble no puede ser nulo");
        }
        Inmueble inmueble = mapper.map(dto);
        inmueble.setId(null);
        inmueble.setUsuario(usuario);
        return inmuebleRepository.save(inmueble);
    }

    @Override
    public Page<Inmueble> listarInmuebles() {
        return null;
    }

    @Override
    public Inmueble buscarInmueblePorId(Long id) {
        Optional<Inmueble> inmueble = inmuebleRepository.findById(id);
        return inmueble.orElseThrow(()-> new InmuebleNoEncontradoException("Inmueble no encontrado"));
    }

    @Override
    @Transactional
    public Inmueble actualizarInmueble(InmuebleDTO dto) {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        if (dto == null) {
            throw new IllegalArgumentException("Inmueble no puede ser nulo");
        }

        if (dto.id() == null) {
            throw new IllegalArgumentException("El id no puede ser nulo para realizar esta acción");
        }

        Inmueble inmuebleEncontrado = inmuebleRepository.findById(dto.id())
                .orElseThrow(() -> new InmuebleNoEncontradoException("Inmueble no encontrado"));
        inmuebleEncontrado = mapper.map(dto);
        inmuebleEncontrado.setUsuario(usuario);
        return inmuebleRepository.save(inmuebleEncontrado);
    }

    @Override
    @Transactional
    public void eliminarInmueble(Long id) {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));



        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo para realizar esta acción");
        }

        Inmueble inmueble = inmuebleRepository.findById(id)
                .orElseThrow(() -> new InmuebleNoEncontradoException("Inmueble no encontrado"));

        if (usuario.getId() != inmueble.getUsuario().getId()) {
            throw new UsuarioSinPermisoException("No tienes permiso para realizar esta acción");
        }
        inmuebleRepository.deleteById(id);
    }

}
