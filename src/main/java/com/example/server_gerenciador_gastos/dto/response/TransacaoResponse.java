package com.example.server_gerenciador_gastos.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoResponse(
        String id,
        BigDecimal valor,
        String tipo,
        String idContaOrigem,
        String idContaDestino,
        String idCarteiraDestino,
        LocalDateTime data
) {
}
