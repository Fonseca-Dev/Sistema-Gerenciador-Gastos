package com.example.server_gerenciador_gastos.repository;

import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.entity.Carteira;
import com.example.server_gerenciador_gastos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarteiraRepository extends JpaRepository<Carteira,String> {
    List<Carteira> findByContaId(String contaId);
}
