package idforideas.bonpland.security;

import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.UsuarioNoEncontradoException;
import idforideas.bonpland.repository.UsuarioRepository;
import jakarta.servlet.ServletException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Figueroa Mauro
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UsuarioRepository repository;

    public CustomUserDetailService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = repository.findByCorreo(correo).orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
        return new CustomUserDetails(usuario);
    }
}
