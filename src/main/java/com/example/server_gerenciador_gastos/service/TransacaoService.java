package com.example.server_gerenciador_gastos.service;

import com.example.server_gerenciador_gastos.entity.Carteira;
import com.example.server_gerenciador_gastos.entity.Transacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static java.util.Arrays.stream;


@Service
public class TransacaoService {
    public void transferenciaCarteiras(Carteira carteiraOrigem, Carteira carteiraDestino, BigDecimal valorTransacao){
        BigDecimal saldoOrigem = carteiraOrigem.getSaldo();
        BigDecimal saldoDestino = carteiraDestino.getSaldo();

        if(saldoOrigem.compareTo(valorTransacao)<0) {
            carteiraOrigem.setSaldo(saldoOrigem.subtract(valorTransacao));
            carteiraDestino.setSaldo(saldoDestino.add(valorTransacao));
        }
    }

    public List<Transacao> filtrarPorValor(List<Transacao> transacoes, BigDecimal valorMax, BigDecimal valorMin){
        List<Transacao> listaFiltrada = transacoes.stream()
                .filter(t -> t.getValor().compareTo(valorMin) > 0 && t.getValor().compareTo(valorMax) < 0)
                .toList();

        return listaFiltrada;
    }

    public List<Transacao> filtrarPorCategoria(List<Transacao> transacoes, String categoria){
        List<Transacao> listaFiltrada = transacoes.stream()
                .filter(t -> Objects.equals(t.getCategoria(), categoria))
                .toList();

        return listaFiltrada;
    }

}
