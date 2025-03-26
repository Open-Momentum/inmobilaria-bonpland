package idforideas.bonpland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idforideas.bonpland.config.SecurityConfig;
import idforideas.bonpland.dto.DTO;
import idforideas.bonpland.dto.inmuebles.InmuebleDTO;
import idforideas.bonpland.entities.Inmueble;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.impl.InmuebleMapper;
import idforideas.bonpland.security.CustomUserDetailService;
import idforideas.bonpland.security.JwtService;
import idforideas.bonpland.service.InmuebleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static idforideas.bonpland.utils.TestUtil.getInmuebleDto;
import static idforideas.bonpland.utils.TestUtil.getUsuario;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InmuebleController.class)
@ExtendWith(MockitoExtension.class)
@Import({SecurityConfig.class, InmuebleMapper.class})
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

    }
}