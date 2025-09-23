package com.example.server_gerenciador_gastos.service;

import com.example.server_gerenciador_gastos.dto.request.CriarTransacaoRequest;
import com.example.server_gerenciador_gastos.dto.response.CriarTransacaoResponse;
import com.example.server_gerenciador_gastos.dto.response.ListarTransacoesResponse;
import com.example.server_gerenciador_gastos.entity.*;
import com.example.server_gerenciador_gastos.repository.CarteiraRepository;
import com.example.server_gerenciador_gastos.repository.ContaRepository;
import com.example.server_gerenciador_gastos.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Service
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
    public CriarTransacaoResponse criarTransacao(CriarTransacaoRequest request) {
        if (Objects.isNull(request) || request.valor().compareTo(BigDecimal.ZERO) <= 0) {
            return new CriarTransacaoResponse("VALOR_INVALIDO", null);
        }

        Transacao transacao = new Transacao(request.valor());

        switch (request.tipo().toUpperCase()) {
            case "DEPOSITO": {
                if (request.idContaDestino() == null || request.idContaDestino().isEmpty()) {
                    return new CriarTransacaoResponse("CONTA_DESTINO_OBRIGATORIA", null);
                }
                Conta contaDestino = contaRepository.findById(request.idContaDestino()).orElse(null);
                if (contaDestino == null) {
                    return new CriarTransacaoResponse("CONTA_DESTINO_NAO_ENCONTRADA", null);
                }
                transacao.processarDeposito(contaDestino);
                break;
            }
            case "SAQUE": {
                if (request.idContaOrigem() == null || request.idContaOrigem().isEmpty()) {
                    return new CriarTransacaoResponse("CONTA_ORIGEM_OBRIGATORIA", null);
                }
                Conta contaOrigem = contaRepository.findById(request.idContaOrigem()).orElse(null);
                if (contaOrigem == null) {
                    return new CriarTransacaoResponse("CONTA_ORIGEM_NAO_ENCONTRADA", null);
                }
                transacao.processarSaque(contaOrigem);
                break;
            }
            case "CONTA_CARTEIRA": {
                if (request.idContaOrigem() == null || request.idContaOrigem().isEmpty()) {
                    return new CriarTransacaoResponse("CONTA_ORIGEM_OBRIGATORIA", null);
                }
                Conta contaOrigem = contaRepository.findById(request.idContaOrigem()).orElse(null);
                if (contaOrigem == null) {
                    return new CriarTransacaoResponse("CONTA_ORIGEM_NAO_ENCONTRADA", null);
                }
                if (request.idCarteiraDestino() == null || request.idCarteiraDestino().isEmpty()) {
                    return new CriarTransacaoResponse("CARTEIRA_DESTINO_OBRIGATORIA", null);
                }
                Carteira carteiraDestino = carteiraRepository.findById(request.idCarteiraDestino()).orElse(null);
                if (carteiraDestino == null) {
                    return new CriarTransacaoResponse("CARTEIRA_DESTINO_NAO_ENCONTRADA", null);
                }
                transacao.processarContaParaCarteira(contaOrigem,carteiraDestino);
                break;
            }
            case "CARTEIRA_CONTA": {
                if (request.idCarteiraOrigem() == null || request.idCarteiraOrigem().isEmpty()) {
                    return new CriarTransacaoResponse("CARTEIRA_ORIGEM_OBRIGATORIA", null);
                }
                Carteira carteiraOrigem = carteiraRepository.findById(request.idCarteiraOrigem()).orElse(null);
                if (carteiraOrigem == null) {
                    return new CriarTransacaoResponse("CARTEIRA_ORIGEM_NAO_ENCONTRADA", null);
                }
                if (request.idContaDestino() == null || request.idContaDestino().isEmpty()) {
                    return new CriarTransacaoResponse("CONTA_DESTINO_OBRIGATORIA", null);
                }
                Conta contaDestino = contaRepository.findById(request.idContaDestino()).orElse(null);
                if (contaDestino == null) {
                    return new CriarTransacaoResponse("CONTA_DESTINO_NAO_ENCONTRADA", null);
                }
                transacao.processarCarteiraParaConta(carteiraOrigem,contaDestino);
                break;
            }
            case "TRANSFERENCIA_CONTA": {
                if (request.idContaOrigem() == null || request.idContaOrigem().isEmpty()) {
                    return new CriarTransacaoResponse("CONTA_ORIGEM_OBRIGATORIA", null);
                }
                Conta contaOrigem = contaRepository.findById(request.idContaOrigem()).orElse(null);
                if (contaOrigem == null) {
                    return new CriarTransacaoResponse("CONTA_ORIGEM_NAO_ENCONTRADA", null);
                }
                if (request.idContaDestino() == null || request.idContaDestino().isEmpty()) {
                    return new CriarTransacaoResponse("CONTA_DESTINO_OBRIGATORIA", null);
                }
                Conta contaDestino = contaRepository.findById(request.idContaDestino()).orElse(null);
                transacao.processarTransferenciaConta(contaOrigem,contaDestino);
                break;
            }
            default:
                return new CriarTransacaoResponse("Tipo de Transação Inválida.", null);

        }

        transacao.processar();
        Transacao transacaoSalva = transacaoRepository.save(transacao);
        return new CriarTransacaoResponse("SUCESSO", transacaoSalva.getId());
    }

    //READ
    public ListarTransacoesResponse listarTransacoesPorConta(String idConta) {
        List<Transacao> entradas = transacaoRepository.findByIdContaDestino(idConta);
        List<Transacao> saidas = transacaoRepository.findByIdContaOrigem(idConta);

        List<Transacao> transacoes = new ArrayList<>();
        transacoes.addAll(entradas);
        transacoes.addAll(saidas);

        if (transacoes.isEmpty()) {
            return new ListarTransacoesResponse("Nenhuma transação encontrada para essa conta.", null);
        }

        return new ListarTransacoesResponse("Transações encontradas.", transacoes);
    }

}
