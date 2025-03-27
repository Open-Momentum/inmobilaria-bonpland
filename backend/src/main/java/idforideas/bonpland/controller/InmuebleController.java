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
    public ResponseEntity<InmuebleResponseDTO> crearInmueble(@Valid @RequestBody InmuebleDTO inmuebleDTO) {
        Inmueble inmueble = inmuebleService.guardarInmueble(inmuebleDTO);
        InmuebleResponseDTO dto = mapper.map(inmueble);

        return ResponseEntity.created(buildURI(inmueble.getId()))
                .body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InmuebleResponseDTO> buscarPorId(@PathVariable Long id) {
        Inmueble inmueble = inmuebleService.buscarInmueblePorId(id);
        InmuebleResponseDTO dto = mapper.map(inmueble);
        return ResponseEntity.ok().body(dto);
    }

    private static URI buildURI(Long id) {
        return URI.create("/api/inmuebles/" + id);
    }
}
