package idforideas.bonpland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.entities.Rol;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.MapperSimple;
import idforideas.bonpland.mapper.impl.UsuarioRespuestaMapper;
import idforideas.bonpland.service.UsuarioService;
import org.junit.jupiter.api.Test;

import static idforideas.bonpland.utils.TestUtil.getUsuario;
import static idforideas.bonpland.utils.TestUtil.getUsuarioDTO;
import static org.mockito.Mockito.*;

import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = AuthController.class)
@Import(UsuarioRespuestaMapper.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UsuarioService usuarioService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaCrearUsuarioYRetornar201() throws Exception {

        //GIVEN
        when(usuarioService.guardarUsuario(getUsuarioDTO())).thenReturn(getUsuario());

        //WHEN
        mockMvc.perform(post("/api/auth/registro")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getUsuarioDTO())))

                //THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("test"))
                .andExpect(jsonPath("$.apellido").value("test"))
                .andExpect(jsonPath("$.correo").value("test@mail.com"))
                .andDo(print());
    }



}