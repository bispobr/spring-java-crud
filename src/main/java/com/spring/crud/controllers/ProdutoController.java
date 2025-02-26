package com.spring.crud.controllers;

import com.spring.crud.domain.produto.Produto;
import com.spring.crud.domain.produto.ProdutoRepository;
import com.spring.crud.domain.produto.RequisicaoProduto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public ResponseEntity getTodosprodutos(){
        var todosProdudos = repository.findAll();
        return ResponseEntity.ok(todosProdudos);

    }

    @PostMapping
    public ResponseEntity cadastrarProduto(@RequestBody @Valid RequisicaoProduto data){
        Produto novoProduto = new Produto(data);
        repository.save(novoProduto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public  ResponseEntity atualizarProduto(@RequestBody @Valid RequisicaoProduto data){
        Optional<Produto> optionalProduto = repository.findById(data.id());
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(data.nome());
            produto.setPreco(data.preco());
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping
    @Transactional
    public  ResponseEntity DeletarProduto(@RequestBody @Valid RequisicaoProduto data){
        Optional<Produto> optionalProduto = repository.findById(data.id());
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            repository.deleteById(produto.getId());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
