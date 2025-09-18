package com.example.server_gerenciador_gastos.service;


import com.example.server_gerenciador_gastos.entity.Transacao;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CarteiraService {

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
            if (dia == t.getDataTransacao().getDayOfMonth() && mes == t.getDataTransacao().getMonthValue() && ano == t.getDataTransacao().getYear()){
                soma = soma.add(t.getValor());
            }
        }
        return soma;
    }

}
