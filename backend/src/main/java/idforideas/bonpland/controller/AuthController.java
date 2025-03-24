package idforideas.bonpland.controller;

import idforideas.bonpland.dto.auth.LoginDTO;
import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.dto.usuarios.UsuarioRespuestaDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.CredencialesIncorrectasException;
import idforideas.bonpland.exception.UsuarioNoEncontradoException;
import idforideas.bonpland.mapper.impl.UsuarioRespuestaMapper;
import idforideas.bonpland.security.CustomUserDetails;
import idforideas.bonpland.security.JwtService;
import idforideas.bonpland.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Figueroa Mauro
 */
@Tag(name = "Autenticación", description = "Operaciones de autenticación de usuarios")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final UsuarioRespuestaMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UsuarioService usuarioService, UsuarioRespuestaMapper mapper, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @ApiResponse(responseCode = "201")
    @Operation(summary = "Registrar un usuario")
    @PostMapping("/registro")
    public ResponseEntity<UsuarioRespuestaDTO> registrarUsuario(@Valid @RequestBody UsuarioCompletoDTO usuarioCompletoDTO) {
        Usuario usuario = usuarioService.guardarUsuario(usuarioCompletoDTO);
        UsuarioRespuestaDTO dto = mapper.map(usuario);

        return ResponseEntity.created(buildURI(usuario.getId())).body(dto);
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Loguearse con correo y contraseña")
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginDTO loginRequest) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(loginRequest.correo(), loginRequest.clave());
        Authentication authentication = autenticarse(authToken);

        CustomUserDetails usuario = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.generarToken(usuario);
        Map<String,String> respuesta = generarRespuesta( token);
        return ResponseEntity.ok(respuesta);
    }

    private Map<String, String> generarRespuesta(String token) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("Token", token);
        return respuesta;
    }

    private Authentication autenticarse(Authentication authToken) {
        Authentication authentication;
        try {
            authentication =  authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            throw new CredencialesIncorrectasException("Credenciales incorrectas");
        }
        return authentication;
    }


    private static URI buildURI(Long id) {
        return URI.create("/api/usuarios/" + id);
    }

}
