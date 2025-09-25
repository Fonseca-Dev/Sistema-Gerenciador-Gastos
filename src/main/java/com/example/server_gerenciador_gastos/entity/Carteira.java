package com.example.server_gerenciador_gastos.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotBlank
    private String id;
    @NotBlank
    private String nome;
    @NotNull
    private BigDecimal saldo;
    @NotNull
    private BigDecimal meta;
    String IdConta;

    /// Muitas carteiras pertencem a uma conta
    @ManyToOne
    @JsonIgnoreProperties("carteiras")
    private Conta conta;

    /// Uma carteira registra muitas transações
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "carteira", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("carteira")
    private List<Transacao> transacoes = new ArrayList<>();


    public Carteira(String nome, BigDecimal saldo, BigDecimal meta, String IdConta) {
        this.nome = nome;
        this.saldo = saldo;
        this.meta = meta;
        this.IdConta = IdConta;
    }
}

