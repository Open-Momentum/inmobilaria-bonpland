package idforideas.bonpland.controller;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.dto.inmuebles.InmuebleResponseDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.mapper.impl.InmuebleRespuestaMapper;
import idforideas.bonpland.service.InmuebleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

/**
 * @author Figueroa Mauro
 */
@RestController
@RequestMapping("/api/inmuebles")
@AllArgsConstructor
public class InmuebleController {

    private final InmuebleService inmuebleService;
    private final InmuebleRespuestaMapper mapper;


    @PostMapping
    public ResponseEntity<InmuebleResponseDTO> crearInmueble(@Valid @RequestBody InmuebleDTO requestDto) {
        Inmueble inmueble = inmuebleService.guardarInmueble(requestDto);
        InmuebleResponseDTO responseDto = mapper.map(inmueble);

        return ResponseEntity.created(buildURI(inmueble.getId()))
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<?> listarInmuebles (@PageableDefault(size = 10, page = 0)
                                                  Pageable pageable,
                                              PagedResourcesAssembler<InmuebleResponseDTO> pagedResourcesAssembler) {

        Page<InmuebleResponseDTO> inmuebles = inmuebleService.listarInmuebles(pageable).map(mapper::map);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(inmuebles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InmuebleResponseDTO> buscarPorId(@PathVariable Long id) {
        Inmueble inmueble = inmuebleService.buscarInmueblePorId(id);
        InmuebleResponseDTO responseDto = mapper.map(inmueble);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<InmuebleResponseDTO> actualizarInmueble(@RequestBody InmuebleDTO requestDto) {
        Inmueble inmueble = inmuebleService.actualizarInmueble(requestDto);
        InmuebleResponseDTO responseDto = mapper.map(inmueble);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InmuebleResponseDTO> eliminarPorId(@PathVariable Long id) {
        inmuebleService.eliminarInmueble(id);
        return ResponseEntity.noContent().build();
    }

    private static URI buildURI(Long id) {
        return URI.create("/api/inmuebles/" + id);
    }
}
