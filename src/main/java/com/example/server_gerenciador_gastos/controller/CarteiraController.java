package com.example.server_gerenciador_gastos.controller;

import com.example.server_gerenciador_gastos.dto.CategoriaDTO;
import com.example.server_gerenciador_gastos.dto.request.CriarCarteiraRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.dto.response.ListarTransacoesResponse;
import com.example.server_gerenciador_gastos.entity.Carteira;
import com.example.server_gerenciador_gastos.service.CarteiraService;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/carteiras")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarteiraController {

    private final CarteiraService carteiraService;

    public CarteiraController(CarteiraService carteiraService) {
        this.carteiraService = carteiraService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> criarCarteira(@RequestBody @Valid final CriarCarteiraRequest request){
        BaseResponse response = carteiraService.criarCarteira(request);
        return ResponseEntity.status(response.status()).body(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> buscarCarteiras(){
        // A lógica do serviço já deve retornar o BaseResponse
        return ResponseEntity.ok(carteiraService.listarCarteiras());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> atualizarCarteira(
            @PathVariable UUID id, // Padronizado para UUID
            @RequestBody @Valid CriarCarteiraRequest requestDTO) {

        return carteiraService.atualizarCarteira(id, requestDTO)
                .map(carteiraAtualizada -> new BaseResponse("Carteira atualizada com sucesso.", HttpStatus.OK, carteiraAtualizada))
                .map(response -> ResponseEntity.ok(response))
                .orElse(new ResponseEntity<>(new BaseResponse("Carteira não encontrada.", HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        carteiraService.deleteCarteira(id);
    }
}