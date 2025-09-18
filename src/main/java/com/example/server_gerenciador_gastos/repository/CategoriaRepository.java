package com.example.server_gerenciador_gastos.repository;

import com.example.server_gerenciador_gastos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository  extends JpaRepository<Categoria,String> {
    Optional<Categoria> findByNome(String nome);
}
