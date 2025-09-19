package com.example.server_gerenciador_gastos.service;

import com.example.server_gerenciador_gastos.dto.CategoriaDTO;
import com.example.server_gerenciador_gastos.entity.Categoria;
import com.example.server_gerenciador_gastos.mapper.CategoriaMapper;
import com.example.server_gerenciador_gastos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    // GET ALL
    public List<CategoriaDTO> getAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY NOME
    public Optional<CategoriaDTO> findByNome(String nome) {
        return categoriaRepository.findByNome(nome)
                .map(categoriaMapper::toDTO);
    }

    // POST
    public Optional<CategoriaDTO> cadastrarCategoria(CategoriaDTO categoriaDTO) {
        Optional<Categoria> existente = categoriaRepository.findByNome(categoriaDTO.getNome());
        if (existente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria já existe!");
        }

        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        categoria.setId(null); // força criação de novo ID

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return Optional.ofNullable(categoriaMapper.toDTO(categoriaSalva));
    }

    // PUT
    public Optional<CategoriaDTO> atualizarCategoria(CategoriaDTO categoriaDTO) {
        if (categoriaDTO.getId() == null || !categoriaRepository.existsById(categoriaDTO.getId())) {
            return Optional.empty();
        }

        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria categoriaAtualizada = categoriaRepository.save(categoria);
        return Optional.ofNullable(categoriaMapper.toDTO(categoriaAtualizada));
    }

    // DELETE
    public boolean delete(String id) {
        Optional<Categoria> checkId = categoriaRepository.findById(id);

        if (checkId.isEmpty()) {
            return false;
        }
        categoriaRepository.deleteById(id);
        return true;
    }
}
