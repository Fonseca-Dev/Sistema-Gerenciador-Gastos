package com.example.server_gerenciador_gastos.dto.request;

import com.example.server_gerenciador_gastos.entity.Categoria;

import java.math.BigDecimal;

public record CriarTransacaoRequest(
        BigDecimal valor,
        String tipo,
        String idContaOrigem,
        String idContaDestino,
        String idCarteiraOrigem,
        String idCarteiraDestino,
        String nomeCategoria

) {
}
