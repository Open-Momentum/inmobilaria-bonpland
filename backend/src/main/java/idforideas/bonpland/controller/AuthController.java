package idforideas.bonpland.controller;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.dto.usuarios.UsuarioRespuestaDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.impl.UsuarioRespuestaMapper;
import idforideas.bonpland.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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

    public AuthController(UsuarioService usuarioService, UsuarioRespuestaMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }
    @ApiResponse(responseCode = "201")
    @Operation(summary = "Registrar un usuario")
    @PostMapping("/registro")
    public ResponseEntity<UsuarioRespuestaDTO> registrarUsuario(@Valid @RequestBody UsuarioCompletoDTO usuarioCompletoDTO) {
        Usuario usuario = usuarioService.guardarUsuario(usuarioCompletoDTO);
        UsuarioRespuestaDTO dto = mapper.map(usuario);
        return ResponseEntity.created(buildURI(usuario.getId())).body(dto);

    }

    private static URI buildURI(Long id) {
        return URI.create("/api/usuarios/" + id);
    }

}
