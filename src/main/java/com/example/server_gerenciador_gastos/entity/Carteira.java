package com.example.server_gerenciador_gastos.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name= "tb_carteira")
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private BigDecimal saldo;
    private BigDecimal meta;

}
