package com.br.protegemeucerrado.ocorrencia;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.sql.Date;
import java.util.List;

import com.br.protegemeucerrado.ocorrencia.controller.OcorrenciaController;
import com.br.protegemeucerrado.ocorrencia.model.Ocorrencia;
import com.br.protegemeucerrado.ocorrencia.service.OcorrenciaService;
import com.br.protegemeucerrado.ocorrencia.model.Categoria;
import com.br.protegemeucerrado.ocorrencia.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OcorrenciaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OcorrenciaControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private OcorrenciaService ocServ;

        private Ocorrencia ocorrencia;
        private Status status;
        private Categoria categoria;

        @BeforeEach
        public void setUp() {
                MockitoAnnotations.openMocks(this);
                ocorrencia = new Ocorrencia();
                ocorrencia.setId(1);
                ocorrencia.setId_user(1);
                ocorrencia.setId_categoria(1);
                ocorrencia.setId_status(1);
                ocorrencia.setNome("Test Name");
                ocorrencia.setEmail("test@example.com");
                ocorrencia.setCpf("123.456.789-00");
                ocorrencia.setTelefone("123456789");
                ocorrencia.setDt_nasc(Date.valueOf("2000-01-01"));
                ocorrencia.setDescricao("Test Description");
                ocorrencia.setIs_anon(false);
                ocorrencia.setDt_ocorrencia(Date.valueOf("2023-01-01"));
                ocorrencia.setLat("10.0000");
                ocorrencia.setLon("20.0000");

                status = new Status();
                status.setId(1);
                status.setNome_status("Test Status");

                categoria = new Categoria();
                categoria.setId(1);
                categoria.setNome_categoria("Test Category");

                ocorrencia.setId_status(status.getId());
                ocorrencia.setId_categoria(categoria.getId());
        }

        @Test
        @WithMockUser
        public void testListarOcorrencias() throws Exception {
                List<Ocorrencia> ocorrencias = Arrays.asList(ocorrencia);
                when(ocServ.listarOcorrencias()).thenReturn(ocorrencias);

                mockMvc.perform(get("/ocorrencias"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[0].descricao").value("Test Description"));
        }

        @Test
        @WithMockUser
        public void testCadastrarOcorrencia() throws Exception {
                when(ocServ.cadastrarOcorrencia(any(Ocorrencia.class))).thenReturn(true);

                mockMvc.perform(post("/ocorrencias")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\": 1, \"id_user\": 1, \"id_categoria\": 1, \"id_status\": 1, \"nome\": \"Test Name\", \"email\": \"test@example.com\", \"cpf\": \"123.456.789-00\", \"telefone\": \"123456789\", \"dt_nasc\": \"2000-01-01\", \"descricao\": \"Test Description\", \"is_anon\": false, \"dt_ocorrencia\": \"2023-01-01\", \"lat\": \"10.0000\", \"lon\": \"20.0000\"}"))
                                .andExpect(status().isCreated())
                                .andExpect(content().string("Ocorrência cadastrada com sucesso!"));
        }

        @Test
        @WithMockUser
        public void testEditarOcorrencia() throws Exception {
                when(ocServ.editarOcorrencia(any(Ocorrencia.class))).thenReturn(true);

                mockMvc.perform(put("/ocorrencias")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\": 1, \"id_user\": 1, \"id_categoria\": 1, \"id_status\": 1, \"nome\": \"Updated Name\", \"email\": \"updated@example.com\", \"cpf\": \"123.456.789-00\", \"telefone\": \"123456789\", \"dt_nasc\": \"2000-01-01\", \"descricao\": \"Updated Description\", \"is_anon\": false, \"dt_ocorrencia\": \"2023-01-01\", \"lat\": \"10.0000\", \"lon\": \"20.0000\"}"))
                                .andExpect(status().isCreated())
                                .andExpect(content().string("Ocorrência editada com sucesso!"));
        }

        @Test
        @WithMockUser
        public void testExcluirOcorrencia() throws Exception {
                when(ocServ.excluirOcorrencia(1)).thenReturn(true);

                mockMvc.perform(delete("/ocorrencias/1"))
                                .andExpect(status().isCreated())
                                .andExpect(content().string("Ocorrência deletada com sucesso!"));
        }

        @Test
        @WithMockUser
        public void testDescricaoMaxLength() throws Exception {
                String longDescription = "a".repeat(751); // 751 caracteres

                mockMvc.perform(post("/ocorrencias")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"descricao\": \"" + longDescription
                                                + "\", \"localizacao\": \"Test Location\", \"categoria\": \"Test Category\", \"anonimo\": false}"))
                                .andExpect(status().isBadRequest());
        }

        // Só devem ser aceitas imagens no formato JPG, JPEG, PNG, WEBP
        @Test
        @WithMockUser
        public void testImagemAceita() throws Exception {
                String[] formatosAceitos = {"test.jpg", "test.jpeg", "test.png", "test.webp"};
                for (String formato : formatosAceitos) {
                        mockMvc.perform(post("/ocorrencias")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content("{\"descricao\": \"Test Description\", \"localizacao\": \"Test Location\", \"categoria\": \"Test Category\", \"anonimo\": false, \"imagem\": \"" + formato + "\"}"))
                                        .andExpect(status().isCreated());
                }

                String[] formatosNaoAceitos = {"test.gif", "test.bmp"};
                for (String formato : formatosNaoAceitos) {
                        mockMvc.perform(post("/ocorrencias")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content("{\"descricao\": \"Test Description\", \"localizacao\": \"Test Location\", \"categoria\": \"Test Category\", \"anonimo\": false, \"imagem\": \"" + formato + "\"}"))
                                        .andExpect(status().isBadRequest());
                }
        }
        
}