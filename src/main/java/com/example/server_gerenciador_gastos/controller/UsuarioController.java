package com.example.server_gerenciador_gastos.controller;

import com.example.server_gerenciador_gastos.dto.request.CriarUsuarioRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> cadastrarUsuario(@RequestBody @Valid final CriarUsuarioRequest request) {
        BaseResponse response = service.criarUsuario(request);
        return ResponseEntity.status(response.status()).body(response);
    }
    @GetMapping
    public ResponseEntity<BaseResponse> listarUsuarios() {
        BaseResponse response = service.listarUsuarios();
        return ResponseEntity.status(response.status()).body(response);
    }

    @GetMapping("/{email}")
    public ResponseEntity<BaseResponse> buscarUsuarioPorEmail(@PathVariable final String email){
        BaseResponse response = service.buscarUsuarioPorEmail(email);
        return ResponseEntity.status(response.status()).body(response);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<BaseResponse> deletarUsuario(@PathVariable String email){
        BaseResponse response = service.deletarUsuario(email);
        return ResponseEntity.status(response.status()).body(response);
    }
}
