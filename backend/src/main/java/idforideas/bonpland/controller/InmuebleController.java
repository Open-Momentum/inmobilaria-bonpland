package idforideas.bonpland.controller;

import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.dto.inmuebles.InmuebleResponseDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.mapper.impl.InmuebleMapper;
import idforideas.bonpland.mapper.impl.InmuebleRespuestaMapper;
import idforideas.bonpland.service.InmuebleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
