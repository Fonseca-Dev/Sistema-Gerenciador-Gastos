package com.example.server_gerenciador_gastos.controller;

import com.example.server_gerenciador_gastos.dto.CategoriaDTO;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.entity.Categoria;
import com.example.server_gerenciador_gastos.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> buscarTodos(){
        return ResponseEntity.ok(categoriaService.getAll());
    }

    @GetMapping("/{nome}")
    public ResponseEntity<CategoriaDTO> buscarPorNome (@PathVariable String nome){
        return categoriaService.findByNome(nome)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CategoriaDTO> criarCategoria (@Valid @RequestBody CategoriaDTO categoria){
        return categoriaService.cadastrarCategoria(categoria)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<CategoriaDTO> atualizarCategoria (@Valid @RequestBody CategoriaDTO categoria){
        return categoriaService.atualizarCategoria(categoria)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        categoriaService.delete(id);
    }

    @GetMapping("/maisUsada/conta/{idConta}/mes/{ano}/{mes}")
    public ResponseEntity<BaseResponse> categoriasMaisUsadasPorConta(@PathVariable String idConta, @PathVariable int ano, @PathVariable int mes){
        BaseResponse response = categoriaService.categoriasMaisUsadasPorConta(idConta, ano, mes);
        return ResponseEntity.status(response.status()).body(response);

    }

    @GetMapping("/maiorValor/conta/{idConta}/mes/{ano}/{mes}")
    public ResponseEntity<BaseResponse> categoriaComMaiorValorNoMesPorConta(@PathVariable String idConta, @PathVariable int ano, @PathVariable int mes){
        BaseResponse response = categoriaService.categoriaComMaiorValorNoMesPorConta(idConta, ano, mes);
        return ResponseEntity.status(response.status()).body(response);
    }
}
