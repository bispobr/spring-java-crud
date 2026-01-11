package com.spring.crud.controllers;

import com.spring.crud.dto.RequisicaoProdutoDto;
import com.spring.crud.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@Slf4j
@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    @Operation(description = "Endpoint responsável por listar todos os produtos")
    @ApiResponse (responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity listarTodosprodutos(){
        log.info(" Requisição de Listagem de  produtos recebida");
        return ResponseEntity.ok(service.listarprodutos());

    }

    @GetMapping("/{id}")
    @Operation(description = "Endpoint responsável por listar produto por id")
    @ApiResponse (responseCode = "200", description = "Listagem bem sucedida")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity listarById(@PathVariable("id")String id){
        log.info("solicitação de busca por id recebida;");
        return service.listarPorId(id);

    }

    @PostMapping
    @Operation(description = "Endpoint responsável por cadastrar novos Produtos")
    @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity cadastrarProduto( @RequestBody @Valid RequisicaoProdutoDto data){
        log.info("Solicitação de Cadastro de produto recebida");
        return service.cadastraProduto( data);
    }

    @PutMapping("/{id}")
    @Operation(description = "Endpoint responsável por atualizar Produtos")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public  ResponseEntity atualizarProduto(@PathVariable("id")String id , @RequestBody @Valid RequisicaoProdutoDto data){
        log.info("Solicitação de Atualização de produto recebida");
        return service.atualizarProduto(id,data);

    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint responsável por remover Produto")
    @ApiResponse(responseCode = "200", description = "Produto removido com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public  ResponseEntity deletarProduto(@PathVariable("id")String id){
        log.info("Solicitação de remoção de produto recebida");
        return service.removerProduto(id);

    }


}
