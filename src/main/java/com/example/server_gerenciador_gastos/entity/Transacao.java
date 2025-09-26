package com.example.server_gerenciador_gastos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    protected BigDecimal valor;
    @NotBlank
    private String tipo;
    @NotNull
    private LocalDateTime data;
    protected String idContaOrigem;
    protected String idContaDestino;
    private String idCarteiraOrigem;
    private String idCarteiraDestino;

    public Transacao(BigDecimal valor) {
        this.valor = valor;
        this.data = LocalDateTime.now();
    }

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

    public void processar() {

    }

    public void processarDeposito(Conta contaDestino) {
        this.tipo = "DEPOSITO";
        this.idContaDestino = contaDestino.getId();
        contaDestino.setSaldo(contaDestino.getSaldo().add(this.valor));
        this.data = LocalDateTime.now();
    }

    public void processarSaque(Conta contaOrigem) {
        this.tipo = "SAQUE";
        this.idContaOrigem = contaOrigem.getId();
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(this.valor));
        this.data = LocalDateTime.now();
    }

    public void processarContaParaCarteira(Conta contaOrigem, Carteira carteiraDestino) {
        this.tipo = "CONTA_CARTEIRA";
        this.idContaOrigem = contaOrigem.getId();
        this.idCarteiraDestino = carteiraDestino.getId();
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(this.valor));
        carteiraDestino.setSaldo(carteiraDestino.getSaldo().add(this.valor));
        this.data = LocalDateTime.now();
    }

    public void processarCarteiraParaConta(Carteira carteiraOrigem, Conta contaDestino) {
        this.tipo = "CARTEIRA_CONTA";
        this.idCarteiraOrigem = carteiraOrigem.getId();
        this.idContaDestino = contaDestino.getId();
        carteiraOrigem.setSaldo(carteiraOrigem.getSaldo().subtract(this.valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(this.valor));
        this.data = LocalDateTime.now();
    }

    public void processarTransferenciaConta(Conta contaOrigem, Conta contaDestino, Categoria categoria) {
        this.tipo = "TRANSFERENCIA_CONTA";
        this.idContaOrigem = contaOrigem.getId();
        this.idContaDestino = contaDestino != null ? contaDestino.getId() : null;
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(this.valor));
        if (contaDestino != null) {
            contaDestino.setSaldo(contaDestino.getSaldo().add(this.valor));
        }
        this.data = LocalDateTime.now();
        this.categoria = categoria;
    }

}
