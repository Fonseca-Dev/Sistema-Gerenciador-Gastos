package com.example.server_gerenciador_gastos.service;

import com.example.server_gerenciador_gastos.dto.request.CriarContaRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.dto.response.CriarContaResponse;
import com.example.server_gerenciador_gastos.entity.Conta;
import com.example.server_gerenciador_gastos.entity.Usuario;
import com.example.server_gerenciador_gastos.repository.ContaRepository;
import com.example.server_gerenciador_gastos.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ContaService {
    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;

    public ContaService(UsuarioRepository usuarioRepository, ContaRepository contaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.contaRepository = contaRepository;
    }

    public BaseResponse criarConta(CriarContaRequest request){
        if(Objects.isNull(request)){
            return new BaseResponse("Request está nulo.", HttpStatus.NOT_FOUND,null);
        }

        Usuario titular = usuarioRepository.findById(request.idTitular()).orElseThrow(()->new RuntimeException("Titular não encontrado."));

        Conta contaNova = new Conta();
        contaNova.setSaldo(request.saldo());
        contaNova.setTipo(request.tipo());
        contaNova.setTitular(titular);
        contaRepository.save(contaNova);

        CriarContaResponse response = new CriarContaResponse(
                contaNova.getId(),
                contaNova.getSaldo(),
                contaNova.getTipo(),
                contaNova.getTitular()
        );

        return new BaseResponse("Conta criada com sucesso.",HttpStatus.CREATED, response);
    }

    public BaseResponse listarContas() {
        if (contaRepository.findAll().isEmpty()) {
            return new BaseResponse("Nenhuma conta cadastrada.", HttpStatus.NOT_FOUND, null);
        }
        return new BaseResponse("Contas cadastradas encontradas.", HttpStatus.OK, contaRepository.findAll());
    }

    public boolean deletarConta(String id){
        Optional<Conta> checkId = contaRepository.findById(id);

        if(checkId.isEmpty()){
            return false;
        }
        else {
            contaRepository.deleteById(id);
            return true;
        }
    }
}
