
package idforideas.bonpland.controller;

import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.dto.usuarios.UsuarioRespuestaDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.impl.UsuarioRespuestaMapper;
import idforideas.bonpland.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Usuarios", description = "Gestión de usuarios: actualización, eliminación y consulta de información")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private static final String DEFAULT_PAGE = "{\"page\": 0, \"size\": 10, \"sort\": \"nombre\"}";

    private final UsuarioService usuarioService;
    private final UsuarioRespuestaMapper mapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioRespuestaMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Obtener lista de usuarios")
    @GetMapping
    public ResponseEntity<?> listarUsuarios(@PageableDefault(size = 10 ,sort = "nombre", page = 0)
                                                @Schema(example = DEFAULT_PAGE)
                                                Pageable pageable,
                                            PagedResourcesAssembler<UsuarioRespuestaDTO> pagedResourcesAssembler) {
        Page<UsuarioRespuestaDTO> usuarios = usuarioService.listarUsuarios(pageable).map(mapper::map);

        return ResponseEntity.ok(pagedResourcesAssembler.toModel(usuarios));
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Obtener un usuario específico por su id")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespuestaDTO> buscarUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        return ResponseEntity.ok(mapper.map(usuario));
    }

    @Operation(summary = "Actualizar valores de un usuario específico")
    @PutMapping
    @ApiResponse(responseCode = "200")
    public ResponseEntity<UsuarioRespuestaDTO> registrarUsuario(@Valid @RequestBody UsuarioCompletoDTO usuarioCompletoDTO) {
        Usuario usuario = usuarioService.actualizarUsuario(usuarioCompletoDTO);
        UsuarioRespuestaDTO dto = mapper.map(usuario);

        return ResponseEntity.ok(dto);

    }

    @Operation(summary = "Borrar un usuario específico")
    @ApiResponse(responseCode = "204")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        usuarioService.bajaLogicaUsuario(id);

        return ResponseEntity.noContent().build();
    }
}
