package com.example.server_gerenciador_gastos.service;


import com.example.server_gerenciador_gastos.dto.request.CriarCarteiraRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.entity.Carteira;
import com.example.server_gerenciador_gastos.entity.Transacao;
import com.example.server_gerenciador_gastos.mapper.CarteiraMapper;
import com.example.server_gerenciador_gastos.repository.CarteiraRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;



@Service
public class CarteiraService {

    private final CarteiraRepository repository;

    public CarteiraService(CarteiraRepository repository) {
        this.repository = repository;
    }


    public BaseResponse criarCarteira(CriarCarteiraRequest request) {
        if (Objects.isNull(request)) {
            return new BaseResponse("Request está nulo.", HttpStatus.NO_CONTENT, null);
        }
        Carteira novaCarteira = CarteiraMapper.map(request);
        repository.save(novaCarteira);
        return new BaseResponse("Carteira criada com sucesso!", HttpStatus.CREATED, novaCarteira);
    }

    public BaseResponse listarCarteiras() {
        if (repository.findAll().isEmpty()) {
            return new BaseResponse("Nenhuma carteira cadastrada.", HttpStatus.NOT_FOUND, null);
        }
        return new BaseResponse("Carteiras cadastradas encontradas.", HttpStatus.OK, repository.findAll());
    }













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
