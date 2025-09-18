package com.example.server_gerenciador_gastos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotBlank
    private String id;
    @NotBlank
    private String numero;
    @NotNull
    private BigDecimal saldo;


    @OneToMany(fetch = FetchType.EAGER,mappedBy = "conta", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("conta")
    private List<Carteira> carteiras = new ArrayList<>();


    @OneToOne
    private Usuario usuario;

}
