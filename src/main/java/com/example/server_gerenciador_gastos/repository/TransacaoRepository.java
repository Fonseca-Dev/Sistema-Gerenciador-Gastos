package com.example.server_gerenciador_gastos.repository;

import com.example.server_gerenciador_gastos.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao,String> {
    // Todas as transações de uma conta como origem
    List<Transacao> findByIdContaOrigem(String idContaOrigem);

    // Todas as transações de uma conta como destino
    List<Transacao> findByIdContaDestino(String idContaDestino);

    // Todas as transações em que uma carteira foi usada
    List<Transacao> findByCarteiraId(String idCarteira);
}
