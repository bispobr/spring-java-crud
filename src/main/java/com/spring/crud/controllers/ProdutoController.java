package com.spring.crud.controllers;

import com.spring.crud.domain.Produto;
import com.spring.crud.repository.ProdutoRepository;
import com.spring.crud.dto.RequisicaoProdutoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    @Operation(description = "Endpoint responsável por listar todos os produtos")
    @ApiResponse (responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity listarTodosprodutos(){
        var todosProdudos = repository.findAll();
        log.info("Listagem de todos os produtos Bem sucedida");
        return ResponseEntity.ok(todosProdudos);

    }

    @PostMapping
    @Operation(description = "Endpoint responsável por cadastrar novos Produtos")
    @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity cadastrarProduto(@RequestBody @Valid RequisicaoProdutoDto data){
        log.info("Solicitação de Cadastro de produto recebida");
        Produto novoProduto = new Produto(data);
        repository.save(novoProduto);
        log.info("Novo produto: " +data.nome() +" cadastrado com sucesso");
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    @Operation(description = "Endpoint responsável por atualizar Produtos")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public  ResponseEntity atualizarProduto(@RequestBody @Valid RequisicaoProdutoDto data){
        log.info("Solicitação de Atualização de produto recebida");
        Optional<Produto> optionalProduto = repository.findById(data.id());
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(data.nome());
            produto.setPreco(data.preco());
            log.info("Produto: " +data.nome() +" atualizado com sucesso");
            return ResponseEntity.ok(produto);
        } else {
            log.info("Produto: " +data.nome() +" Não encontrado na base de dados");
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping
    @Transactional
    @Operation(description = "Endpoint responsável por remover Produto")
    @ApiResponse(responseCode = "200", description = "Produto removido com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public  ResponseEntity deletarProduto(@RequestBody @Valid RequisicaoProdutoDto data){
        log.info("Solicitação de remoção de produto recebida");
        Optional<Produto> optionalProduto = repository.findById(data.id());
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            repository.deleteById(produto.getId());
            log.info("Produto: " +data.nome() +" removido com sucesso");
            return ResponseEntity.ok().build();
        } else {
            log.info("Produto: " +data.nome() +" Não encontrado no banco de dados");
            return ResponseEntity.notFound().build();
        }

    }

}
