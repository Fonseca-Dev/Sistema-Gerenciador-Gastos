package com.example.server_gerenciador_gastos.controller;

import com.example.server_gerenciador_gastos.dto.request.CriarContaRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.entity.Conta;
import com.example.server_gerenciador_gastos.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
public class ContaController {
    private final ContaService contaService;

    ContaController(ContaService contaService){
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> criarConta(@RequestBody @Valid final CriarContaRequest contaRequest){
        BaseResponse response = contaService.criarConta(contaRequest);
        return ResponseEntity.status(response.status()).body(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> buscarContas(){
        return ResponseEntity.ok(contaService.listarContas());
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarConta(@PathVariable String id) {
        contaService.deletarConta(id);
    }
}
