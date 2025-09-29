package com.example.server_gerenciador_gastos.dto;

import java.util.List;

public class CategoriaDTO {
    private String id;
    private String nome;
    private List<String> transactionIds;

    public CategoriaDTO(String id, String nome, List<String> transactionIds) {
        this.id = id;
        this.nome = nome;
        this.transactionIds = transactionIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getTransactionIds() {
        return transactionIds;
    }

    public void setTransactionIds(List<String> transactionIds) {
        transactionIds = transactionIds;
    }
}
