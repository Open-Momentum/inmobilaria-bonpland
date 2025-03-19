package idforideas.bonpland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idforideas.bonpland.config.SecurityConfig;
import idforideas.bonpland.dto.DTO;
import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.exception.CorreoExistenteException;
import idforideas.bonpland.mapper.impl.UsuarioRespuestaMapper;
import idforideas.bonpland.security.CustomUserDetailService;
import idforideas.bonpland.security.JwtService;
import idforideas.bonpland.service.UsuarioService;
import org.junit.jupiter.api.Test;

import static idforideas.bonpland.utils.TestUtil.getUsuario;
import static idforideas.bonpland.utils.TestUtil.getUsuarioDTO;
import static org.mockito.Mockito.*;

import static org.hamcrest.Matchers.hasItems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Figueroa Mauro
 */
@WebMvcTest(controllers = AuthController.class)
@ActiveProfiles("test")
@Import({UsuarioRespuestaMapper.class, SecurityConfig.class})
class AuthControllerTest {
    public static final String PATH_REGISTRO = "/api/auth/registro";
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UsuarioService usuarioService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    CustomUserDetailService customUserDetailService;
    @MockitoBean
    private JwtService jwtService;

    @Test
    void deberiaCrearUsuarioYRetornar201() throws Exception {

        //GIVEN
        when(usuarioService.guardarUsuario(getUsuarioDTO())).thenReturn(getUsuario());

        //WHEN
        mockMvc.perform(post(PATH_REGISTRO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getUsuarioDTO())))

                //THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("test"))
                .andExpect(jsonPath("$.apellido").value("test"))
                .andExpect(jsonPath("$.correo").value("test@mail.com"));

        verify(usuarioService, times(1)).guardarUsuario(any(UsuarioCompletoDTO.class));
    }

    @Test
    void deberiaRetornar400YListaDeErrores_cuandoDTOEsInvalido() throws Exception {
        //GIVEN
        DTO dto = new UsuarioCompletoDTO(null, "", "test@", "123123", "correo@mail", "1122334455");

        //WHEN
        mockMvc.perform(post(PATH_REGISTRO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isArray())
                .andExpect(jsonPath("$.error", hasItems("El nombre debe tener entre 2 y 50 caracteres",
                        "Formato de correo inválido","El apellido no puede contener caracteres especiales",
                        "El nombre no puede contener caracteres especiales",
                        "La clave debe tener entre 8 y 20 caracteres")));

        verify(usuarioService,never()).guardarUsuario(any(UsuarioCompletoDTO.class));
    }

    @Test
    void deberiaRetornar400YUnError_cuandoDTOTieneUnCampoInvalido() throws Exception {
        //GIVEN
        UsuarioCompletoDTO dto = getUsuarioDTO();
        dto.setNombre("Error400");

        //WHEN
        mockMvc.perform(post(PATH_REGISTRO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isString())
                .andExpect(jsonPath("$.error").value("El nombre no puede contener caracteres especiales"));

        verify(usuarioService,never()).guardarUsuario(any(UsuarioCompletoDTO.class));
    }

    @Test
    void deberiaRetornar400_cuandoDTOTieneCorreoExistente() throws Exception {
        //GIVEN
        UsuarioCompletoDTO dto = getUsuarioDTO();
        when(usuarioService.guardarUsuario(dto)).thenThrow(new CorreoExistenteException("El correo ya existe"));

        //WHEN
        mockMvc.perform(post(PATH_REGISTRO)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))

                //THEN
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isString())
                .andExpect(jsonPath("$.error").value("El correo ya existe"));

        verify(usuarioService).guardarUsuario(any(UsuarioCompletoDTO.class));
    }


}