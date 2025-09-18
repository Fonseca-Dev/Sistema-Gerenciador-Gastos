package com.example.server_gerenciador_gastos.repository;

import com.example.server_gerenciador_gastos.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta,String> {
}
