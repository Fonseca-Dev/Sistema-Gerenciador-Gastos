package com.example.server_gerenciador_gastos.dto.response;

import java.math.BigDecimal;

public record CategoriaValorResponse(String categoria, BigDecimal valor) {
}
