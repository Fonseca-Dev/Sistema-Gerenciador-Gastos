package com.example.server_gerenciador_gastos.entity;

import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotBlank
    private String id;
    @NotNull
    private BigDecimal saldo;
    @NotBlank
    private String tipo;

    //Muitas contas pertencem a um titular
    @ManyToOne
    @JoinColumn(name = "usuario_id") // cria coluna usuario_id na tabela conta
    @JsonIgnoreProperties("contas")  // evita recursão infinita na serialização
    private Usuario titular;

    // Uma conta pode ter várias transações
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("conta")  // evita recursão infinita na serialização
    private List<Transacao> transacoes = new ArrayList<>();

    // Uma conta pode ter várias carteiras
    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("conta")  // evita recursão infinita na serialização
    private List<Carteira> carteiras = new ArrayList<>();

}
