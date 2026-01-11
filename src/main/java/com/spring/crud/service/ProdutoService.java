package com.spring.crud.service;

import com.spring.crud.domain.Produto;
import com.spring.crud.dto.RequisicaoProdutoDto;
import com.spring.crud.dto.RespostaProdutoDto;
import com.spring.crud.mapper.ProdutoMapper;
import com.spring.crud.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository repository;

    @Autowired
    ProdutoMapper mapper;



    public List<RespostaProdutoDto> listarprodutos(){
        log.info("Listagem de todos os produtos Bem sucedida");
        return  mapper.paraRespostaDtoList(repository.findAll());

    }

    @Cacheable(value = "produto", key = "#id")
    public ResponseEntity listarPorId(String id){
        Optional<Produto> produtoEncontrado= repository.findById(id);
        if (produtoEncontrado.isPresent()){
            Produto produto = produtoEncontrado.get();
            log.info("Produto encontrado");
            return ResponseEntity.ok(mapper.paraRespostaProdutoDto(produto));
        } else {
            log.info("Produto não encontrado");
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity cadastraProduto(RequisicaoProdutoDto data){
        Produto Produto = mapper.paraProduto(data);
        repository.save(Produto);
        log.info("Novo produto: " +data.nome() +" cadastrado com sucesso");
        return ResponseEntity.ok().build();
    }

    @Transactional
    @CachePut(value = "produto", key = "#id")
    public  ResponseEntity atualizarProduto(String id,RequisicaoProdutoDto data){
        Optional<Produto> optionalProduto = repository.findById(id);
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(data.nome());
            produto.setPreco(data.preco());
            produto.setId(id);
            log.info("Produto: " +data.nome() +" atualizado com sucesso");
            return ResponseEntity.ok(mapper.paraRespostaProdutoDto(produto));
        } else {
            log.info("Produto: " +data.nome() +" Não encontrado na base de dados");
            return ResponseEntity.notFound().build();
        }
    }


    @Transactional
    @CacheEvict(value = "produto", key = "#id")
    public  ResponseEntity removerProduto(String id){
        Optional<Produto> optionalProduto = repository.findById(id);
        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            repository.deleteById(produto.getId());
            log.info("Produto:  removido com sucesso");
            return ResponseEntity.ok().build();
        } else {
            log.info("Produto id: " + id  +" Não encontrado no banco de dados");
            return ResponseEntity.notFound().build();
        }
    }
}
