package com.example.server_gerenciador_gastos.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ListarTransacoesResponse(
        String mensagem,
        Object objeto
) {
}
