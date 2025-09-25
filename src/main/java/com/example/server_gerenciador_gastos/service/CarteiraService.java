package com.example.server_gerenciador_gastos.service;


import com.example.server_gerenciador_gastos.dto.request.CriarCarteiraRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.entity.Carteira;
import com.example.server_gerenciador_gastos.entity.Conta;
import com.example.server_gerenciador_gastos.mapper.CarteiraMapper;
import com.example.server_gerenciador_gastos.repository.CarteiraRepository;
import com.example.server_gerenciador_gastos.repository.ContaRepository;
import com.example.server_gerenciador_gastos.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CarteiraService {

    private final CarteiraMapper carteiraMapper;
    private final CarteiraRepository carteiraRepository;
    private final ContaRepository contaRepository;

    public CarteiraService(CarteiraMapper carteiraMapper, CarteiraRepository carteiraRepository, ContaRepository contaRepository) {
        this.carteiraMapper = carteiraMapper;
        this.carteiraRepository = carteiraRepository;
        this.contaRepository = contaRepository;
    }


    public BaseResponse criarCarteira(CriarCarteiraRequest request) {

        Optional<Conta> contaOptional = contaRepository.findById(request.idConta());
        if (contaOptional.isEmpty()) {
            return new BaseResponse("Conta não encontrada.", HttpStatus.BAD_REQUEST, null);
        }

        if (Objects.isNull(request)) {
            return new BaseResponse("Request está nulo.", HttpStatus.NO_CONTENT, null);
        }
        Carteira novaCarteira = CarteiraMapper.map(request);
        novaCarteira.setConta(contaOptional.get());
        carteiraRepository.save(novaCarteira);
        return new BaseResponse("Carteira criada com sucesso!", HttpStatus.CREATED, novaCarteira);
    }

    public BaseResponse listarCarteiras() {
        if (carteiraRepository.findAll().isEmpty()) {
            return new BaseResponse("Nenhuma carteira cadastrada.", HttpStatus.NOT_FOUND, null);
        }
        return new BaseResponse("Carteiras cadastradas encontradas.", HttpStatus.OK, carteiraRepository.findAll());
    }

    /// bgl novo
    public void deleteCarteira(String id) {
        Optional<Carteira> checkId = carteiraRepository.findById(id);
        if (checkId.isEmpty()) {
            return;
        }
        carteiraRepository.deleteById(id);
    }


    public Optional<Carteira> atualizarCarteira(UUID id, CriarCarteiraRequest requestDTO) {
        Optional<Carteira> optionalCarteira = carteiraRepository.findById(id.toString());

        if (optionalCarteira.isEmpty()) {
            return Optional.empty();
        }

        Carteira carteiraExistente = optionalCarteira.get();
        carteiraExistente.setNome(requestDTO.nome());
        carteiraExistente.setSaldo(requestDTO.saldo());
        carteiraExistente.setMeta(requestDTO.meta());

        return Optional.of(carteiraRepository.save(carteiraExistente));

    }

    public BaseResponse listarCarteirasPorConta(String idConta) {
        if (carteiraRepository.findByContaId(idConta).isEmpty()) {
            return new BaseResponse("Nenhuma carteira encontrada.", HttpStatus.NOT_FOUND, null);
        }
        return new BaseResponse("Carteiras cadastradas encontradas.", HttpStatus.OK, carteiraRepository.findByContaId(idConta));    }



/*
    public BigDecimal gastoMensal(List<Transacao> transacoes, int mes, int ano){
        BigDecimal soma = BigDecimal.valueOf(0);

        for(Transacao t : transacoes){
            if (mes == t.getDataTransacao().getMonthValue() && ano == t.getDataTransacao().getYear()){
                soma = soma.add(t.getValor());
            }
        }
        return soma;
    }
    public BigDecimal gastoDiario(List<Transacao> transacoes,  int dia, int mes, int ano){
        BigDecimal soma = BigDecimal.valueOf(0);

        for(Transacao t : transacoes){
            if (
                    dia == t.getDataTransacao().getDayOfMonth() &&
                    mes == t.getDataTransacao().getMonthValue() &&
                    ano == t.getDataTransacao().getYear()
            ){
                soma = soma.add(t.getValor());
            }
        }
        return soma;
    }

 */



}
