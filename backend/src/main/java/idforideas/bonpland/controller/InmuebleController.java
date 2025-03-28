package idforideas.bonpland.controller;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.dto.inmuebles.InmuebleResponseDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.mapper.impl.InmuebleRespuestaMapper;
import idforideas.bonpland.service.InmuebleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author Figueroa Mauro
 */
@Tag(name = "Inmuebles", description = "Gestión de inmuebles: creación, actualización, eliminación y consulta de información")
@RestController
@RequestMapping("/api/inmuebles")
@AllArgsConstructor
public class InmuebleController {
    private static final String DEFAULT_PAGE = "{\"page\": 0, \"size\": 10, \"sort\": \"tipo_propiedad\"}";
    private final InmuebleService inmuebleService;
    private final InmuebleRespuestaMapper mapper;

    @ApiResponse(responseCode = "201")
    @Operation(summary = "Crear inmueble")
    @PostMapping
    public ResponseEntity<InmuebleResponseDTO> crearInmueble(@Valid @RequestBody InmuebleDTO requestDto) {
        Inmueble inmueble = inmuebleService.guardarInmueble(requestDto);
        InmuebleResponseDTO responseDto = mapper.map(inmueble);

        return ResponseEntity.created(buildURI(inmueble.getId()))
                .body(responseDto);
    }

    @ApiResponse(responseCode = "200")
    @Operation(summary = "Obtener lista de inmuebles")
    @GetMapping
    public ResponseEntity<?> listarInmuebles(@PageableDefault(size = 10, page = 0)
                                                 @Schema(example = DEFAULT_PAGE)
                                             Pageable pageable,
                                             PagedResourcesAssembler<InmuebleResponseDTO> pagedResourcesAssembler) {

        Page<InmuebleResponseDTO> inmuebles = inmuebleService.listarInmuebles(pageable).map(mapper::map);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(inmuebles));
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")})
    @Operation(summary = "Obtener inmueble por ID")
    @GetMapping("/{id}")
    public ResponseEntity<InmuebleResponseDTO> buscarPorId(@PathVariable Long id) {
        Inmueble inmueble = inmuebleService.buscarInmueblePorId(id);
        InmuebleResponseDTO responseDto = mapper.map(inmueble);
        return ResponseEntity.ok(responseDto);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")})
    @Operation(summary = "Actualizar valores del inmueble")
    @PutMapping
    public ResponseEntity<InmuebleResponseDTO> actualizarInmueble(@RequestBody InmuebleDTO requestDto) {
        Inmueble inmueble = inmuebleService.actualizarInmueble(requestDto);
        InmuebleResponseDTO responseDto = mapper.map(inmueble);
        return ResponseEntity.ok(responseDto);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404")})
    @Operation(summary = "Eliminar inmueble por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<InmuebleResponseDTO> eliminarPorId(@PathVariable Long id) {
        inmuebleService.eliminarInmueble(id);
        return ResponseEntity.noContent().build();
    }

    private static URI buildURI(Long id) {
        return URI.create("/api/inmuebles/" + id);
    }
}
