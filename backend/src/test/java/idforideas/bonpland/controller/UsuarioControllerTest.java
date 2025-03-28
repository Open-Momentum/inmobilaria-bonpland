package idforideas.bonpland.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import idforideas.bonpland.config.SecurityConfig;
import idforideas.bonpland.dto.usuarios.UsuarioCompletoDTO;
import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.exception.IdInexistenteException;
import idforideas.bonpland.exception.UsuarioNoEncontradoException;
import idforideas.bonpland.mapper.impl.UsuarioRespuestaMapper;
import idforideas.bonpland.security.CustomUserDetailService;
import idforideas.bonpland.security.JwtService;
import idforideas.bonpland.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static idforideas.bonpland.utils.TestUtil.*;
import static org.mockito.Mockito.*;

import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Figueroa Mauro
 */
@WebMvcTest(controllers = UsuarioController.class)
@ActiveProfiles("test")
@Import({UsuarioRespuestaMapper.class, SecurityConfig.class})
class UsuarioControllerTest {
    public static final String PATH_USUARIOS = "/api/usuarios";

    private List<Usuario> lista;
    private Pageable pageable;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UsuarioService usuarioService;
    private ObjectMapper objectMapper;

    @MockitoBean
    CustomUserDetailService customUserDetailService;
    @MockitoBean
    private JwtService jwtService;



    @BeforeEach
    void init() {
        lista = new ArrayList<>();
        pageable = PageRequest.of(0, 10);
        objectMapper = new ObjectMapper();
    }


    @Test
    @WithMockUser(username = "test", roles = "ADMIN")
    void deberiaListarUsuariosYRetornar200() throws Exception {
        //GIVEN
        lista = getUsuarios();
        when(usuarioService.listarUsuarios(any(Pageable.class))).thenReturn(new PageImpl<>(lista, pageable, lista.size()));

        //WHEN
        mockMvc.perform(get(PATH_USUARIOS)
                                .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._embedded.usuarioRespuestaDTOList").isArray())
                .andExpect(jsonPath("$._embedded.usuarioRespuestaDTOList.length()").value(lista.size()))
                .andExpect(jsonPath("$._embedded.usuarioRespuestaDTOList[0].id").value(lista.get(0).getId()))
                .andExpect(jsonPath("$._embedded.usuarioRespuestaDTOList[0].nombre").value(lista.get(0).getNombre()))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$.page.totalElements").value(lista.size()));

//        verify(usuarioService).listarUsuarios(any(Pageable.class));
    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaRetornarListaVaciaY200() throws Exception {
        //GIVEN
        when(usuarioService.listarUsuarios(any(Pageable.class))).thenReturn(new PageImpl<>(lista, pageable, lista.size()));

        //WHEN
        mockMvc.perform(get(PATH_USUARIOS)
                                .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").doesNotExist());

        verify(usuarioService).listarUsuarios(any(Pageable.class));
    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaRetornarUsuarioPorIdY200_cuandoExiste() throws Exception {
        //GIVEN
        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(getUsuario());

        //WHEN
        mockMvc.perform(get(PATH_USUARIOS + "/1")
                                .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("test"))
                .andExpect(jsonPath("$.apellido").value("test"))
                .andExpect(jsonPath("$.correo").value("test@mail.com"));

        verify(usuarioService).buscarUsuarioPorId(anyLong());
    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaRetornar400buscandoPorId_cuandoNoExiste() throws Exception {
        //GIVEN
        when(usuarioService.buscarUsuarioPorId(1L)).thenThrow(new UsuarioNoEncontradoException("Usuario no encontrado"));

        //WHEN
        mockMvc.perform(get(PATH_USUARIOS + "/1")
                                .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.error").value("Usuario no encontrado"));

        verify(usuarioService).buscarUsuarioPorId(anyLong());
    }

    @Test
    @WithMockUser(username = "test", roles = "USUARIO")
    void deberiaActualizarUsuarioYRetornar200() throws Exception {
        //GIVEN
        UsuarioCompletoDTO dto = getUsuarioDTO();
        dto.setId(1L);
        Usuario usuarioActualizado = getUsuario();
        usuarioActualizado.setNombre("Nuevo nombre");
        when(usuarioService.actualizarUsuario(dto)).thenReturn(usuarioActualizado);

        //WHEN
        mockMvc.perform(put(PATH_USUARIOS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))

                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Nuevo nombre"));

        verify(usuarioService).actualizarUsuario(any(UsuarioCompletoDTO.class));
    }

    @Test
    @WithMockUser(username = "test", roles = {"USUARIO"})
    void deberiaRetornar400ActualizandoUsuario_cuandoNoTieneId() throws Exception {
        //GIVEN
        UsuarioCompletoDTO dto = getUsuarioDTO();
        dto.setId(10L);
        when(usuarioService.actualizarUsuario(dto))
                .thenThrow(new IdInexistenteException("El id no puede ser nulo para realizar esta acción"));

        //WHEN
        mockMvc.perform(put(PATH_USUARIOS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))


        //THEN
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.error").value("El id no puede ser nulo para realizar esta acción"));

        verify(usuarioService).actualizarUsuario(any(UsuarioCompletoDTO.class));
    }

    @Test
    @WithMockUser(username = "test", roles = {"ADMIN"})
    void deberiaHacerBorradoLogicoPorId() throws Exception {
        //GIVEN
        Usuario usuario = getUsuario();
        usuario.setActivo(false);
        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuario);
        when(usuarioService.bajaLogicaUsuario(1L)).thenReturn(1);


        //WHEN
        mockMvc.perform(delete(PATH_USUARIOS + "/1")
                                .contentType(MediaType.APPLICATION_JSON))

                //THEN
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

    }
}