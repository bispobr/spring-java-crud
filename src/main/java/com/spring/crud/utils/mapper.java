package com.spring.crud.utils;

import java.util.List;

public interface mapper <Entidade,Dto,Resposta>{
    Entidade paraProduto(Dto dto);
    Resposta paraRespostaProdutoDto(Entidade entity);
    List<Resposta> paraRespostaDtoList (List<Entidade> entidadeList);
}
