package com.example.server_gerenciador_gastos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotBlank
    private String id;
    @NotBlank
    private String nome;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "categoria", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("categoria")
    @NotNull
    List<Transacao> transacao = new ArrayList<>();

    public Categoria(String nome) {
        this.nome = nome;
    }
}
