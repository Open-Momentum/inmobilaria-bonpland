
package idforideas.bonpland.controller;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.dto.usuarios.UsuarioRespuestaDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.impl.UsuarioRespuestaMapper;
import idforideas.bonpland.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Figueroa Mauro
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRespuestaMapper mapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioRespuestaMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<?> listarUsuarios(@PageableDefault(size = 10) Pageable pageable,
                                            PagedResourcesAssembler<UsuarioRespuestaDTO> pagedResourcesAssembler) {
        Page<UsuarioRespuestaDTO> usuarios = usuarioService.listarUsuarios(pageable).map(mapper::map);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(usuarios));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespuestaDTO> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(mapper.map(usuario));
    }

    @PutMapping
    public ResponseEntity<UsuarioRespuestaDTO> registrarUsuario(@Valid @RequestBody UsuarioCompletoDTO usuarioCompletoDTO) {
        Usuario usuario = usuarioService.actualizarUsuario(usuarioCompletoDTO);
        UsuarioRespuestaDTO dto = mapper.map(usuario);
        return ResponseEntity.ok(dto);

    }
}
