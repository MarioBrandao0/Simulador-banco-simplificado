package com.banco.basico.simulador.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Carteira {
    UUID id = UUID.randomUUID();
    BigDecimal saldo = BigDecimal.valueOf(0);
    Usuario usuario;

    public Carteira(Usuario usuario) {
        this.usuario = usuario;
    }
}
