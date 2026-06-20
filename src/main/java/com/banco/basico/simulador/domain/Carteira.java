package com.banco.basico.simulador.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Carteira {
    UUID id;
    float saldo;
    Usuario usuario;

    public Carteira(float saldo, Usuario usuario) {
        this.id = UUID.randomUUID();
        this.saldo = saldo;
        this.usuario = usuario;
    }
}
