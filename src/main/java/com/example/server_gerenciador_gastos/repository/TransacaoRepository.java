package com.example.server_gerenciador_gastos.repository;

import com.example.server_gerenciador_gastos.entity.Transacao;
import com.example.server_gerenciador_gastos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao,String> {
}
