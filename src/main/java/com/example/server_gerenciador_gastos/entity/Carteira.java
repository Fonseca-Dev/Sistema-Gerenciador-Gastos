package com.example.server_gerenciador_gastos.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
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

    // Muitas carteiras pertencem a um usuario
    @ManyToOne
    @JsonIgnoreProperties("carteiras")
    private Usuario usuario;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "carteira", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("carteira")
    private List<Transacao> transacoes = new ArrayList<>();

    public Carteira() {
    }
}
