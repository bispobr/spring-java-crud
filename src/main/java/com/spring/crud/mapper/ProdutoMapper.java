package com.spring.crud.mapper;

import com.spring.crud.domain.Produto;
import com.spring.crud.dto.RequisicaoProdutoDto;
import com.spring.crud.dto.RespostaProdutoDto;
import com.spring.crud.utils.mapper;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper implements mapper <Produto,RequisicaoProdutoDto,RespostaProdutoDto> {

    @Override
    public Produto paraProduto(RequisicaoProdutoDto requisicao) {
        Produto produto = new Produto();
        produto.setNome(requisicao.nome());
        produto.setPreco(requisicao.preco());
        return produto;
    }

    @Override
    public  RespostaProdutoDto  paraRespostaProdutoDto(Produto produto) {
        return  new RespostaProdutoDto(produto.getId(), produto.getNome(), produto.getPreco());
    }

    @Override
    public List<RespostaProdutoDto> paraRespostaDtoList(List<Produto> produtos) {
        return produtos.stream()
                .map(this::paraRespostaProdutoDto)
                .collect(Collectors.toList());
    }
    }