package com.example.server_gerenciador_gastos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.OnOpen;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotBlank
    private String id;
    @NotNull
    private BigDecimal valor;
    @NotBlank
    private String tipo;
    @NotNull
    private LocalDateTime data;
    @NotBlank
    private String idContaOrigem;
    @NotBlank
    private String idContaDestino;


    // Muitas transações pertencem a uma carteira
    @ManyToOne
    @JsonIgnoreProperties("transacoes")
    private Carteira carteira;

    // Muitas transações podem ter a mesma categoria
    @ManyToOne
    @JsonIgnoreProperties("transacoes")
    private Categoria categoria;

    // Muitas transações podem ter a uma conta
    @ManyToOne
    @JsonIgnoreProperties("transacoes")
    private Conta conta;

}
