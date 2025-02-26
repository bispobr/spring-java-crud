package com.spring.crud.domain.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequisicaoProduto(String id, @NotBlank String nome, @NotNull Integer preco) {
}
