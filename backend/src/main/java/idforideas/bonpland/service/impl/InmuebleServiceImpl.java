package idforideas.bonpland.service.impl;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Foto;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.IdInexistenteException;
import idforideas.bonpland.exception.InmuebleNoEncontradoException;
import idforideas.bonpland.exception.UsuarioNoEncontradoException;
import idforideas.bonpland.exception.UsuarioSinPermisoException;
import idforideas.bonpland.mapper.impl.InmuebleMapper;
import idforideas.bonpland.repository.InmuebleRepository;
import idforideas.bonpland.repository.UsuarioRepository;
import idforideas.bonpland.security.CustomUserDetailService;
import idforideas.bonpland.service.InmuebleService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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
        inmueble.setCodigo(crearCodigo(inmueble));

        return inmuebleRepository.save(inmueble);
    }


    @Override
    public Page<Inmueble> listarInmuebles(Pageable pageable) {
        return inmuebleRepository.findAll(pageable);
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
        String codigo = inmuebleEncontrado.getCodigo();
        List<Foto> fotos = inmuebleEncontrado.getFotos();
        inmuebleEncontrado = mapper.map(dto);
        inmuebleEncontrado.setUsuario(usuario);
        inmuebleEncontrado.setCodigo(codigo);
        inmuebleEncontrado.setFotos(fotos);
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
            throw new IdInexistenteException("El id no puede ser nulo para realizar esta acción");
        }
    }

    private void validarDtoNulo(InmuebleDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Inmueble no puede ser nulo");
        }
    }


    private @NotNull String crearCodigo(Inmueble inmueble) {
        StringBuilder st = new StringBuilder();
        String tipo = inmueble.getTipoPropiedad().name().substring(0, 2);
        Long id = inmueble.getUsuario().getId();

        String fechaFormateada = formatearFecha();

        return st.append(tipo)
                .append("-")
                .append(id)
                .append("-")
                .append(fechaFormateada)
                .toString();

    }

    private String formatearFecha() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return localDateTime.format(formatter);
    }
}
