package com.example.server_gerenciador_gastos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record CriarCarteiraRequest(
        @NotBlank String nome,
        @NotNull BigDecimal saldo,
        @NotNull BigDecimal meta,

        String idConta
)
{

}
