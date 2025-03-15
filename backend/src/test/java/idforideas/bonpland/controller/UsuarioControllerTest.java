package idforideas.bonpland.controller;

import idforideas.bonpland.entities.Usuario;
import idforideas.bonpland.mapper.impl.UsuarioRespuestaMapper;
import idforideas.bonpland.service.UsuarioService;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static idforideas.bonpland.utils.TestUtil.getUsuarios;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Figueroa Mauro
 */
@WebMvcTest(controllers = UsuarioController.class)
@ActiveProfiles("test")
@Import(UsuarioRespuestaMapper.class)
class UsuarioControllerTest {
    public static final String PATH_USUARIOS = "/api/usuarios";

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    void deberiaListarUsuariosYRetornar200() throws Exception {
        //GIVEN
        List<Usuario> lista = getUsuarios();
        Pageable pageable = PageRequest.of(0, 10);
        when(usuarioService.listarUsuarios(pageable)).thenReturn(new PageImpl<>(lista, pageable, lista.size()));

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
    }
}