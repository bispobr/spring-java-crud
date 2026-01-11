package com.spring.crud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RespostaProdutoDto(@NotNull String id, @NotBlank String nome, @NotNull Integer preco) {
}
