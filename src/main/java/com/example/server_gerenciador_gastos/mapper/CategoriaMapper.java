package com.example.server_gerenciador_gastos.mapper;

import com.example.server_gerenciador_gastos.dto.CategoriaDTO;
import com.example.server_gerenciador_gastos.entity.Categoria;
import com.example.server_gerenciador_gastos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriaMapper {
    @Autowired
    CategoriaRepository categoriaRepository;

    //converte categoria pra categoriaDTO
    public CategoriaDTO toDTO (Categoria categoria){
        List<String> transactionIds = categoria.getTransacao().stream()
                .map(transacao -> transacao.getId())
                .collect(Collectors.toList());
        return new CategoriaDTO(categoria.getId(), categoria.getNome(), transactionIds);
    }

    //converte categoriaDTO pra categoria
    public static Categoria toEntity(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria(categoriaDTO.getNome());
        categoria.setId(categoriaDTO.getId());
        return categoria;
    }
}
