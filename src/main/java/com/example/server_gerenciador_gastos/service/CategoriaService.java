package com.example.server_gerenciador_gastos.service;

import com.example.server_gerenciador_gastos.dto.CategoriaDTO;  // Importando o DTO
import com.example.server_gerenciador_gastos.entity.Categoria;
import com.example.server_gerenciador_gastos.mapper.CategoriaMapper;
import com.example.server_gerenciador_gastos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    CategoriaMapper categoriaMapper;  // Usando o mapper

    // GET

    public ResponseEntity<List<CategoriaDTO>> getAll() {
        List<Categoria> categorias = categoriaRepository.findAll();

        // Converte todas as categorias para DTO
        List<CategoriaDTO> categoriaDTOs = categorias.stream()
                .map(categoriaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoriaDTOs);
    }

    public ResponseEntity<CategoriaDTO> findByNome(String nome) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findByNome(nome);
        return categoriaOpt
                .map(categoria -> ResponseEntity.ok(categoriaMapper.toDTO(categoria)))  // Mapeando para DTO
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST

    public ResponseEntity<CategoriaDTO> post(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO); // Convertendo DTO para entidade
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaMapper.toDTO(categoriaSalva));  // Retorna o DTO
    }

    // PUT

    public ResponseEntity<CategoriaDTO> put(CategoriaDTO categoriaDTO) {
        if (categoriaRepository.existsById(categoriaDTO.getId())) {
            Categoria categoria = categoriaMapper.toEntity(categoriaDTO);  // Convertendo DTO para entidade
            Categoria categoriaAtualizada = categoriaRepository.save(categoria);
            return ResponseEntity.ok(categoriaMapper.toDTO(categoriaAtualizada));  // Retorna o DTO atualizado
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // DELETE

    public void delete(String id) {
        Optional<Categoria> checkId = categoriaRepository.findById(id);

        if (checkId.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            categoriaRepository.deleteById(id);
    }
}
