package com.example.server_gerenciador_gastos.dto.response;

import org.springframework.http.HttpStatus;

public record BaseResponse(String mensagem, HttpStatus status, Object objeto) {
}
