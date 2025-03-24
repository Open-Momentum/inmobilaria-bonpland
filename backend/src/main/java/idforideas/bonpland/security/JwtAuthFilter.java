package idforideas.bonpland.security;

import idforideas.bonpland.exception.UsuarioNoEncontradoException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Figueroa Mauro
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, CustomUserDetailService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().contains("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = obtenerToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String correo = obtenerCorreo(token, response);
        if (correo == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        CustomUserDetails usuario = obtenerUsuario(response, correo);
        if (usuario == null) {
            return;
        }

        autenticarUsuario(request, token, usuario);
        filterChain.doFilter(request, response);

    }




    private String obtenerToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;
    }


    private String obtenerCorreo(String token, HttpServletResponse response) throws IOException {
        try {
            return jwtService.extraerCorreo(token);
        } catch (JwtException e) {
            enviarError(response, e.getMessage());
            return null;
        }
    }

    private void autenticarUsuario(HttpServletRequest request, String token, CustomUserDetails usuario) {
        if (jwtService.esTokenValido(token, usuario)) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private CustomUserDetails obtenerUsuario(HttpServletResponse response, String correo) throws IOException {
        CustomUserDetails usuario = null;
        try {
            usuario = (CustomUserDetails) userDetailsService.loadUserByUsername(correo);
        } catch (UsuarioNoEncontradoException e) {
            enviarError(response, e.getMessage());
            return null;
        }
        return usuario;
    }

    private void enviarError(HttpServletResponse response, String mensaje) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\": \"" + mensaje + "\"}");
    }
}
