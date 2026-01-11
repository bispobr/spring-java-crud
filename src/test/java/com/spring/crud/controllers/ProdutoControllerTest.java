package com.spring.crud.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.crud.dto.RequisicaoProdutoDto;
import com.spring.crud.dto.RespostaProdutoDto;
import com.spring.crud.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProdutoControllerTest {

    @Mock
    ProdutoService service;

    @InjectMocks
    ProdutoController produtoController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
    }

    @Test
    @DisplayName("Listagem de todos os produtos Sucesso")
    void listarTodosprodutos_ListarProdutos_RetornaTodosProdutos() throws Exception {

        List<RespostaProdutoDto> listaResposta ;

        listaResposta = List.of(
                new RespostaProdutoDto("1","lapis",10),
                new RespostaProdutoDto("2","caneta",20),
                new RespostaProdutoDto("3","papel",30));

        when(service.listarprodutos()).thenReturn(listaResposta);

        mockMvc.perform(get("/produto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists())
                .andExpect(jsonPath("$[2]").exists());

        verify(service,times(1)).listarprodutos();

    }

    @Test
    @DisplayName("Listar Produto Existente por ID Retorno 200")
    void listarById_ProdutoExistente_RetornoProduto() throws Exception {

        RespostaProdutoDto produto;
        produto =  new RespostaProdutoDto("1", "lapis", 10);

        when(service.listarPorId("1")).thenReturn(ResponseEntity.ok(produto));

        mockMvc.perform(get("/produto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("lapis"))
                .andExpect(jsonPath("$.preco").value(10));


        verify(service,times(1)).listarPorId("1");
    }

    @Test
    @DisplayName("Listar Produto Retornar 404 não Existente por ID")
    void listarById_ProdutoInexistente_RetornoNotFound() throws Exception {

        when(service.listarPorId("4")).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/produto/4"))
                .andExpect(status().isNotFound());

        verify(service,times(1)).listarPorId("4");
    }

    @Test
    @DisplayName("Cadastra produto Retorna 200")
    void cadastrarProduto_novoCadastro_RetornoHttpStatusOK() throws Exception {

        RequisicaoProdutoDto data;
        data = new RequisicaoProdutoDto("1",10);

        mockMvc.perform(post("/produto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());

        verify(service, times(1)).cadastraProduto(data);
    }

    @Test
    @DisplayName("Atualizar Retorna 200 produto existente no banco de dados")
    void atualizarProduto_ProdutoExistente_RetornoProdutoAtualizado()  throws Exception{

        RequisicaoProdutoDto data= new RequisicaoProdutoDto("caneta",3);
        RespostaProdutoDto resposta= new RespostaProdutoDto("2","caneta",3);
        String id = "2";

        when(service.atualizarProduto(id,data)).thenReturn(ResponseEntity.ok(resposta));

        mockMvc.perform(put("/produto/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nome").value("caneta"))
                .andExpect(jsonPath("$.preco").value(3))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Atualizar Retorna 404 produto inexistente no banco de dados")
    void atualizarProduto_ProdutoInexistente_RetornoNotFound()  throws Exception{

        String id = "99";
        RequisicaoProdutoDto data= new RequisicaoProdutoDto("inexistente",99999);

        when(service.atualizarProduto(id,data)).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(put("/produto/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Deletar Retorna 200 produto existente no banco de dados")
    void deletarProduto_ProdutoExistente_RetornoHttpStatusOK() throws Exception {

        String id = "200";
        when(service.removerProduto(id)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/produto/200"))
                .andExpect(status().isOk());

        verify(service, times(1)).removerProduto(id);
    }

    @Test
    @DisplayName("Deletar Retorna 404 produto inexistente no banco de dados")
    void deletarProduto_ProdutoInexistente_RetornoNotFound() throws Exception {

        String id = "404";
        when(service.removerProduto(id)).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(delete("/produto/404"))
                .andExpect(status().isNotFound());
    }


}