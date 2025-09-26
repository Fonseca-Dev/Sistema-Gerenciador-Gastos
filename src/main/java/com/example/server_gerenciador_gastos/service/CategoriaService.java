package com.example.server_gerenciador_gastos.service;

import com.example.server_gerenciador_gastos.dto.CategoriaDTO;
import com.example.server_gerenciador_gastos.dto.response.BaseResponse;
import com.example.server_gerenciador_gastos.dto.response.CategoriaValorResponse;
import com.example.server_gerenciador_gastos.entity.Categoria;
import com.example.server_gerenciador_gastos.entity.Transacao;
import com.example.server_gerenciador_gastos.mapper.CategoriaMapper;
import com.example.server_gerenciador_gastos.repository.CategoriaRepository;
import com.example.server_gerenciador_gastos.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {


    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, TransacaoRepository transacaoRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.transacaoRepository = transacaoRepository;
        this.categoriaMapper = categoriaMapper;
    }

    private final TransacaoRepository transacaoRepository;
    private final CategoriaMapper categoriaMapper;

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

    public BaseResponse categoriasMaisUsadasPorConta(String idConta, int ano, int mes) {
        List<Transacao> entradas = transacaoRepository.findByIdContaDestino(idConta);
        List<Transacao> saidas = transacaoRepository.findByIdContaOrigem(idConta);

        List<Transacao> todasTransacoes = new ArrayList<>();
        todasTransacoes.addAll(entradas);
        todasTransacoes.addAll(saidas);

        Map<Categoria, Long> contagem = todasTransacoes.stream()
                .filter(t -> t.getData().getYear() == ano && t.getData().getMonthValue() == mes)
                .filter(t -> t.getCategoria() != null)
                .collect(Collectors.groupingBy(
                        Transacao::getCategoria,
                        Collectors.counting()
                ));

        if (contagem.isEmpty()) {
            return new BaseResponse("Nenhuma categoria encontrada.", HttpStatus.NOT_FOUND, null);
        }


        // encontra a maior quantidade
        long max = contagem.values().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        // retorna todas as categorias com a contagem == max
        List<Categoria> categoriasMaisUsadas = contagem.entrySet().stream()
                .filter(entry -> entry.getValue() == max)
                .map(Map.Entry::getKey)
                .toList();

        if (categoriasMaisUsadas.isEmpty()) {
            return new BaseResponse("Nenhuma categoria encontrada.", HttpStatus.NOT_FOUND, null);
        }

        return new BaseResponse("Categorias mais utilizadas.", HttpStatus.OK, categoriasMaisUsadas);
    }

    public BaseResponse categoriaComMaiorValorNoMesPorConta(String idConta, int ano, int mes) {
        List<Transacao> entradas = transacaoRepository.findByIdContaDestino(idConta);
        List<Transacao> saidas = transacaoRepository.findByIdContaOrigem(idConta);

        List<Transacao> todasTransacoes = new ArrayList<>();
        todasTransacoes.addAll(entradas);
        todasTransacoes.addAll(saidas);

        // agrupa por categoria e soma os valores
        Map<Categoria, BigDecimal> somaPorCategoria = todasTransacoes.stream()
                .filter(t -> t.getData().getYear() == ano && t.getData().getMonthValue() == mes)
                .filter(t -> t.getCategoria() != null)
                .collect(Collectors.groupingBy(
                        Transacao::getCategoria,
                        Collectors.mapping(
                                Transacao::getValor,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        if (somaPorCategoria.isEmpty()) {
            return new BaseResponse("Nenhuma transação encontrada nesse mês para a conta.", HttpStatus.NOT_FOUND, null);
        }

        // pega a categoria com maior valor total
        Map.Entry<Categoria, BigDecimal> maior = somaPorCategoria.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();

        CategoriaValorResponse response = new CategoriaValorResponse(maior.getKey().getNome(),maior.getValue());

        return new BaseResponse(
                "Categoria com maior valor no mês encontrada!",
                HttpStatus.OK, response
        );
    }


}
