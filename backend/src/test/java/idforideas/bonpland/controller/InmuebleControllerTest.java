package idforideas.bonpland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idforideas.bonpland.config.SecurityConfig;
import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.enumerations.TipoPropiedad;
import idforideas.bonpland.exception.IdInexistenteException;
import idforideas.bonpland.exception.InmuebleNoEncontradoException;
import idforideas.bonpland.mapper.impl.InmuebleMapper;
import idforideas.bonpland.mapper.impl.InmuebleRespuestaMapper;
import idforideas.bonpland.security.CustomUserDetailService;
import idforideas.bonpland.security.JwtService;
import idforideas.bonpland.service.InmuebleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static idforideas.bonpland.utils.TestUtil.getInmuebleDto;
import static idforideas.bonpland.utils.TestUtil.getUsuario;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InmuebleController.class)
@ExtendWith(MockitoExtension.class)
@Import({SecurityConfig.class, InmuebleMapper.class, InmuebleRespuestaMapper.class})
class InmuebleControllerTest {
    private InmuebleDTO dto;
    private Inmueble inmueble;
    @MockitoBean
    private InmuebleService inmuebleService;
    private InmuebleMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    CustomUserDetailService customUserDetailService;

    @BeforeEach
    void init() {
        mapper = new InmuebleMapper();
        dto = getInmuebleDto();
        inmueble = mapper.map(dto);
    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaCrearInmuebleYRetornar201() throws Exception {
        //GIVEN
        when(inmuebleService.guardarInmueble(dto)).thenReturn(inmueble);

        //WHEN
        mockMvc.perform(post("/api/inmuebles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))

                //THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descripcion").value("descripcion"))
                .andExpect(jsonPath("$.direccion").value("direccion"));

        verify(inmuebleService).guardarInmueble(dto);

    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaBuscarInmuebleYRetornar200() throws Exception {
        //GIVEN
        when(inmuebleService.buscarInmueblePorId(1L)).thenReturn(inmueble);

        //WHEN
        mockMvc.perform(get("/api/inmuebles/1")
                        .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descripcion").value("descripcion"))
                .andExpect(jsonPath("$.direccion").value("direccion"));

        verify(inmuebleService).buscarInmueblePorId(1L);
    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaBuscarInmuebleYRetornar404_cuandoNoLoEncuentra() throws Exception {
        //GIVEN
        when(inmuebleService.buscarInmueblePorId(1L))
                .thenThrow(new InmuebleNoEncontradoException("Inmueble no encontrado"));

        //WHEN
        mockMvc.perform(get("/api/inmuebles/1")
                        .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.error").value("Inmueble no encontrado"));

        verify(inmuebleService).buscarInmueblePorId(1L);
    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaActualizarInmueble() throws Exception {
        //GIVEN
        when(inmuebleService.actualizarInmueble(dto)).thenReturn(inmueble);

        //WHEN
        mockMvc.perform(put("/api/inmuebles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))

                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.id()))
                .andExpect(jsonPath("$.descripcion").value(dto.descripcion()));

        verify(inmuebleService).actualizarInmueble(dto);

    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaRetornar400ActualizandoConDTOSinId() throws Exception {
        //GIVEN
        InmuebleDTO dtoSinId = new InmuebleDTO(null,
                "test",
                "test",
                1000,
                10,
                3,
                3,
                3,
                100,
                TipoPropiedad.CASA);
        when(inmuebleService.actualizarInmueble(dtoSinId))
                .thenThrow(new IdInexistenteException("El id no puede ser nulo para realizar esta acción"));

        //WHEN
        mockMvc.perform(put("/api/inmuebles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoSinId)))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.error").value("El id no puede ser nulo para realizar esta acción"));

        verify(inmuebleService).actualizarInmueble(dtoSinId);
    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaEliminarInmuebleYRetornar204() throws Exception {
        //WHEN
        mockMvc.perform(delete("/api/inmuebles/1")
                        .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isNoContent());

        verify(inmuebleService).eliminarInmueble(1L);
    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaRetornar404EliminandoPorId_cuandoNoExiste() throws Exception {
        //WHEN

        doThrow(new InmuebleNoEncontradoException("Inmueble no encontrado"))
                .when(inmuebleService).eliminarInmueble(1L);

        mockMvc.perform(delete("/api/inmuebles/1")
                        .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isNotFound());

        verify(inmuebleService).eliminarInmueble(1L);
    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaListarInmuebles() throws Exception {
        //WHEN
        List<Inmueble> lista = getInmuebles();

        mockMvc.perform(get("/api/inmuebles")
                        .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isOk());

        verify(inmuebleService).listarInmuebles(any(Pageable.class));
    }


    public static List<Inmueble> getInmuebles() {
        List<Inmueble> lista = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Inmueble actual = new Inmueble();
            actual.setId((long) i + 1);
            lista.add(actual);
        }
        return lista;
    }
}