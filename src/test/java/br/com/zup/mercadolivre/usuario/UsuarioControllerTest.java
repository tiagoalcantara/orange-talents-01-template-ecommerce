package br.com.zup.mercadolivre.usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveriaResponder200CasoCadastreUmUsuario() throws Exception {
        URI uri = new URI("/usuario");
        String json = "{\"email\":\"teste@email.com\",\"senha\":\"senhasegura\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                                              .content(json)
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status()
                                               .isOk());
    }

    @Test
    public void deveriaResponder400CasoTenhaDadosInvalidos() throws Exception {
        URI uri = new URI("/usuario");
        String json = "{\"email\":\"emailinvalido\",\"senha\":\"curta\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                                              .content(json)
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status()
                                               .isBadRequest());
    }

}