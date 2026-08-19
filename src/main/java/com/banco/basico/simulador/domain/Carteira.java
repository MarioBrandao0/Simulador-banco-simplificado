package com.banco.basico.simulador.domain;

import com.banco.basico.simulador.exceptions.SaldoInsuficienteException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "carteiras")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carteira {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(nullable = false, precision = 19, scale = 2)
    BigDecimal saldo = BigDecimal.valueOf(1000);

    public void depositar(BigDecimal valor) {
        validarValor(valor);

        this.saldo = this.saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        validarValor(valor);

        if (this.saldo.compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        this.saldo = this.saldo.subtract(valor);
    }

    private void validarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor deve ser maior que zero"
            );
        }
    }

}
