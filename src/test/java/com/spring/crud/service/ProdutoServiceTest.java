package com.spring.crud.service;

import com.spring.crud.domain.Produto;
import com.spring.crud.dto.RequisicaoProdutoDto;
import com.spring.crud.dto.RespostaProdutoDto;
import com.spring.crud.mapper.ProdutoMapper;
import com.spring.crud.repository.ProdutoRepository;
import jakarta.validation.constraints.AssertFalse;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @Mock
    private ProdutoMapper produtoMapper;


    @Autowired
    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarprodutos_RetornarProdutos_RetornarProdutosBaseDeDados() {

        List<Produto> produtos = List.of(
                new Produto ("52","fogão",3200),
                new Produto("85","cama",200),
                new Produto("24","sofa",3000));


        List<RespostaProdutoDto> listaProdutosDto = List.of(
                new RespostaProdutoDto("52","fogão",3200),
                new RespostaProdutoDto("85","cama",200),
                new RespostaProdutoDto("85","sofa",200));


        when(repository.findAll()).thenReturn(produtos);
        when(produtoMapper.paraRespostaDtoList(repository.findAll())).thenReturn(listaProdutosDto);

        List<RespostaProdutoDto> resultado = produtoService.listarprodutos();

        assertNotNull(resultado);
        assertEquals(3,resultado.size());
        assertEquals("fogão", resultado.get(0).nome());
        assertEquals("cama", resultado.get(1).nome());
        assertEquals("sofa", resultado.get(2).nome());

        verify(repository,times(2)).findAll();
        verify(produtoMapper,times(1)).paraRespostaDtoList(repository.findAll());
    }

    @Test
    void listarPorId_ProdutoExiste_RetornaProduto() {

        Optional<Produto> produtoEncontrado = Optional.of(new Produto("200", "Liquidificador", 200));
        RespostaProdutoDto resposta =  new RespostaProdutoDto("200", "Liquidificador", 200);

        when(repository.findById("200")).thenReturn(produtoEncontrado);
        when(produtoMapper.paraRespostaProdutoDto(any())).thenReturn(resposta);

        ResponseEntity resultado = produtoService.listarPorId("200");

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK ,resultado.getStatusCode());
        assertEquals(resposta, resultado.getBody());

        verify(repository,times(1)).findById("200");
    }

    @Test
    void listarPorId_ProdutoInexistente_RetornaNotFound() {

        String id = "404";

        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity resultado = produtoService.listarPorId("404");

        assertNotNull(resultado);
        assertEquals(HttpStatus.NOT_FOUND,resultado.getStatusCode());
        assertNull(resultado.getBody());

        verify(repository,times(1)).findById("404");


    }

    @Test
    void cadastraProduto_NovoProduto_RetornaHttpStatusOK() {

        RequisicaoProdutoDto data = new RequisicaoProdutoDto("Geladeira",1500);

        ResponseEntity resultado = produtoService.cadastraProduto(data);

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK,resultado.getStatusCode());

        verify(repository,times(1)).save(any());
    }

    @Test
    void atualizarProduto_ProdutoExistente_RetornaProdutoAtualizado() {
        String id = "200";
        RequisicaoProdutoDto data = new RequisicaoProdutoDto("Geladeira",1800);
        Produto produto = new Produto("200", "Geladeira", 100);
        RespostaProdutoDto produtodto = new RespostaProdutoDto("200", "Geladeira", 1800);

        when(repository.findById(id)).thenReturn(Optional.of(produto));
        when(produtoMapper.paraRespostaProdutoDto(produto)).thenReturn(produtodto);

        ResponseEntity resultado = produtoService.atualizarProduto(id,data);


        assertEquals(HttpStatus.OK,resultado.getStatusCode());
        assertEquals(produtodto,resultado.getBody());

        verify(repository, times(1)).findById(id);
        verify(produtoMapper, times(1)).paraRespostaProdutoDto(produto);

        assertEquals(id, produto.getId());
        assertEquals(data.nome(), produto.getNome());
        assertEquals(data.preco(), produto.getPreco());
    }

    @Test
    void atualizarProduto_ProdutoInexistente_RetornaNotFound() {
        String id = "404";
        RequisicaoProdutoDto data = new RequisicaoProdutoDto("celular",3800);

        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity resultado = produtoService.atualizarProduto(id,data);

        assertNull(resultado.getBody());
        assertEquals(HttpStatus.NOT_FOUND,resultado.getStatusCode());

        verify(repository, times(1)).findById(id);
        verify(produtoMapper, never()).paraRespostaProdutoDto(any());

    }

    @Test
    void removerProduto_ProdutoExistente_RetornaHttpStatusOK() {
        String id = "200";
        Produto produto = new Produto("200","celular",500);

        when(repository.findById(id)).thenReturn(Optional.of(produto));

        ResponseEntity resultado = produtoService.removerProduto(id);

        assertNull(resultado.getBody());
        assertEquals(HttpStatus.OK,resultado.getStatusCode());

        verify(repository, times(1)).deleteById(id);

    }

    @Test
    void removerProduto_ProdutoInexistente_RetornaNotFound() {
        String id = "404";

        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity resultado = produtoService.removerProduto(id);

        assertNull(resultado.getBody());
        assertEquals(HttpStatus.NOT_FOUND,resultado.getStatusCode());



    }
}