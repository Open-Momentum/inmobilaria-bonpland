package idforideas.bonpland.controller;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.dto.usuarios.UsuarioRegisterResponseDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.MapperSimple;
import idforideas.bonpland.mapper.impl.UsuarioRespuestaMapper;
import idforideas.bonpland.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final UsuarioService usuarioService;
    private final UsuarioRespuestaMapper mapper;

    public AuthController(UsuarioService usuarioService, UsuarioRespuestaMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioRegisterResponseDTO> registrarUsuario(@RequestBody UsuarioCompletoDTO usuarioCompletoDTO) {
        Usuario usuario = usuarioService.guardarUsuario(usuarioCompletoDTO);
        UsuarioRegisterResponseDTO dto = mapper.map(usuario);
        return ResponseEntity.created(URI.create("/api/usuarios/" + usuario.getId())).body(dto);

    }

}
