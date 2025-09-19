package com.example.server_gerenciador_gastos.dto.response;

import com.example.server_gerenciador_gastos.entity.Usuario;

import java.math.BigDecimal;

public record CriarContaResponse(
        String id,
        BigDecimal saldo,
        String tipo,
        Usuario titular
) {
}
