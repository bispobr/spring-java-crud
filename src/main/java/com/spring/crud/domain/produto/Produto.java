package com.spring.crud.domain.produto;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "produto")
@Entity(name = "produto")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String id;
    private String nome;
    private Integer preco;

    public Produto(RequisicaoProduto requisicaoProduto){
        this.nome = requisicaoProduto.nome();
        this.preco = requisicaoProduto.preco();
    }
}
