package com.example.server_gerenciador_gastos.controller;

import com.example.server_gerenciador_gastos.dto.CategoriaDTO;
import com.example.server_gerenciador_gastos.dto.request.CriarCarteiraRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.service.CarteiraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(carteiraService.listarCarteiras());
    }
}