package com.example.server_gerenciador_gastos.dto.request;

import java.math.BigDecimal;

public record CriarContaRequest(
        BigDecimal saldo,
        String tipo,
        String idTitular
) {
}
