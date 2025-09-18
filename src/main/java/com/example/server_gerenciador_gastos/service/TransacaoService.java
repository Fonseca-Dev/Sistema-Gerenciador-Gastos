package com.example.server_gerenciador_gastos.service;

import com.example.server_gerenciador_gastos.dto.request.CriarTransacaoRequest;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.dto.response.TransacaoResponse;
import com.example.server_gerenciador_gastos.entity.Carteira;
import com.example.server_gerenciador_gastos.entity.Conta;
import com.example.server_gerenciador_gastos.entity.Transacao;
import com.example.server_gerenciador_gastos.repository.CarteiraRepository;
import com.example.server_gerenciador_gastos.repository.ContaRepository;
import com.example.server_gerenciador_gastos.repository.TransacaoRepository;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TransacaoService {
    private final CarteiraRepository carteiraRepository;
    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public TransacaoService(
            CarteiraRepository carteiraRepository,
            ContaRepository contaRepository,
            TransacaoRepository transacaoRepository) {
        this.carteiraRepository = carteiraRepository;
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    //CREATE
    public BaseResponse criarTransacao(CriarTransacaoRequest request) {
        if (Objects.isNull(request)) {
            return new BaseResponse("Request está nulo.", HttpStatus.NOT_FOUND, null);
        }

        Conta contaOrigem = contaRepository.findById(request.idContaOrigem()).orElseThrow(() -> new RuntimeException("Conta não encontrada."));

        Transacao transacao = new Transacao();
        transacao.setValor(request.valor());
        transacao.setTipo(request.tipo());
        transacao.setData(LocalDateTime.now());
        transacao.setIdContaOrigem(request.idContaOrigem());

        if ("CONTA_CONTA".equalsIgnoreCase(request.tipo())) {
            Conta contaDestino = contaRepository.findById(request.idContaDestino()).orElseThrow(() -> new RuntimeException("Conta de destino não encontrada."));
            transacao.setIdContaDestino(request.idContaDestino());


            //Atualiza saldos
            contaDestino.setSaldo(contaDestino.getSaldo().add(request.valor()));
            contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(request.valor()));
            contaRepository.save(contaDestino);
            contaRepository.save(contaOrigem);
        } else if ("CONTA_CARTEIRA".equalsIgnoreCase(request.tipo())) {
            Carteira carteiraDestino = carteiraRepository.findById(request.idCarteiraDestino()).orElseThrow(() -> new RuntimeException("Carteira de destino não encontrada."));
            transacao.setCarteira(carteiraDestino);

            //Atualiza saldos
            carteiraDestino.setSaldo(carteiraDestino.getSaldo().add(request.valor()));
            contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(request.valor()));
            carteiraRepository.save(carteiraDestino);
            contaRepository.save(contaOrigem);
        }

        transacaoRepository.save(transacao);

        TransacaoResponse response = new TransacaoResponse(
                transacao.getId(),
                transacao.getValor(),
                transacao.getTipo(),
                transacao.getIdContaOrigem(),
                transacao.getIdContaDestino(),
                transacao.getCarteira() != null ? transacao.getCarteira().getId() : null,
                transacao.getData()
        );

        return new BaseResponse("Transação criada com sucesso.", HttpStatus.CREATED, response);
    }

    //READ
    public BaseResponse listarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        if (transacoes.isEmpty()) {
            return new BaseResponse("Nenhuma transação encontrada.", HttpStatus.NOT_FOUND, null);
        }
        return new BaseResponse("Transações encontradas.", HttpStatus.OK, transacoes);
    }
}
