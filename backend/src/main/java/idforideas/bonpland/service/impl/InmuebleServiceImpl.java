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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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
        Usuario usuario = obtenerUsuarioAutenticado();
        validarDtoNulo(dto);

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

        return inmueble.orElseThrow(() -> new InmuebleNoEncontradoException("Inmueble no encontrado"));
    }

    @Override
    @Transactional
    public Inmueble actualizarInmueble(InmuebleDTO dto) {
        Usuario usuario = obtenerUsuarioAutenticado();
        validarDtoNulo(dto);
        validarIdNulo(dto.id());

        Inmueble inmuebleEncontrado = inmuebleRepository.findById(dto.id())
                .orElseThrow(() -> new InmuebleNoEncontradoException("Inmueble no encontrado"));
        inmuebleEncontrado = mapper.map(dto);
        inmuebleEncontrado.setUsuario(usuario);

        return inmuebleRepository.save(inmuebleEncontrado);
    }

    @Override
    @Transactional
    public void eliminarInmueble(Long id) {
        Usuario usuario = obtenerUsuarioAutenticado();
        validarIdNulo(id);

        Inmueble inmueble = inmuebleRepository.findById(id)
                .orElseThrow(() -> new InmuebleNoEncontradoException("Inmueble no encontrado"));

        verificarDuenioDeInmueble(usuario, inmueble);
        inmuebleRepository.deleteById(id);
    }



    private Usuario obtenerUsuarioAutenticado() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
    }

    private void verificarDuenioDeInmueble(Usuario usuario, Inmueble inmueble) {
        if (!Objects.equals(usuario.getId(), inmueble.getUsuario().getId())) {
            throw new UsuarioSinPermisoException("No tienes permiso para realizar esta acción");
        }
    }

    private void validarIdNulo(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo para realizar esta acción");
        }
    }

    private void validarDtoNulo(InmuebleDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Inmueble no puede ser nulo");
        }
    }
}
