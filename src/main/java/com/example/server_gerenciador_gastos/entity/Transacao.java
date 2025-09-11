package com.example.server_gerenciador_gastos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.OnOpen;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    // Muitas transações pertencem a uma carteira
    @ManyToOne
    @JsonIgnoreProperties("transacoes")
    private Carteira carteira;

    // Muitas transações podem ter a mesma categoria
    @ManyToOne
    @JsonIgnoreProperties("transacoes")
    private Categoria categoria;

}
