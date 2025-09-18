package com.example.server_gerenciador_gastos.entity;

import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotBlank
    private String id;
    private BigDecimal saldo;
    //Muitas contas pertencem a um titular
    @ManyToOne
    @JsonIgnoreProperties("contas")
    private Usuario titular;

}
