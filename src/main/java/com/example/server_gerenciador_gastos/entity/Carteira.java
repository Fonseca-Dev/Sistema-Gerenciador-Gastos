package com.example.server_gerenciador_gastos.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    @NonNull
    private BigDecimal meta;

    // Muitas carteiras pertencem a uma conta
    @ManyToOne
    @JsonIgnoreProperties("carteiras")
    private Conta conta;

    // Uma carteira pode ter várias transações
    @OneToMany(mappedBy = "carteira", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("carteira")  // evita recursão infinita na serialização
    private List<Transacao> transacoes = new ArrayList<>();


}
