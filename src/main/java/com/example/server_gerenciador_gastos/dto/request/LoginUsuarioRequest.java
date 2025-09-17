package com.example.server_gerenciador_gastos.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginUsuarioRequest(@NotBlank String email, @NotBlank String senha) {
}
