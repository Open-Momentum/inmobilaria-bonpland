package idforideas.bonpland.controller;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.dto.usuarios.UsuarioRegisterResponseDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioRegisterResponseDTO> registrarUsuario(@RequestBody UsuarioCompletoDTO usuarioCompletoDTO) {
        Usuario usuario = usuarioService.guardarUsuario(usuarioCompletoDTO);
        UsuarioRegisterResponseDTO dto = new UsuarioRegisterResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getCorreo());
        return ResponseEntity.created(URI.create("/api/usuarios/" + usuario.getId())).body(dto);

    }

}
