package com.example.server_gerenciador_gastos.dto.request;

import com.example.server_gerenciador_gastos.entity.Carteira;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CriarTransacaoRequest(
        @NotNull BigDecimal valor,
        @NotBlank String tipo,
        @NotBlank String idContaOrigem,
        String idContaDestino,
        String idCarteiraDestino
) {
}
