package com.example.server_gerenciador_gastos.controller;

import com.example.server_gerenciador_gastos.dto.request.CriarTransacaoRequest;
import com.example.server_gerenciador_gastos.dto.response.CriarTransacaoResponse;
import com.example.server_gerenciador_gastos.dto.response.ListarTransacoesResponse;
import com.example.server_gerenciador_gastos.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CriarTransacaoResponse> criarTransacao(@RequestBody CriarTransacaoRequest request){
        CriarTransacaoResponse response = service.criarTransacao(request);
        HttpStatus status = null;
        if(response.mensagem().equals("SUCESSO")){
            status = HttpStatus.CREATED;
        }
        else {
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/conta/{id}")
    public ResponseEntity<ListarTransacoesResponse> listarTransacoesPorConta(@PathVariable String id){
        ListarTransacoesResponse response = service.listarTransacoesPorConta(id);
        HttpStatus status = null;
        if(response.mensagem().equals("Transações encontradas.")){
            status = HttpStatus.OK;
        }
        else {
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status(status).body(response);
    }
}
