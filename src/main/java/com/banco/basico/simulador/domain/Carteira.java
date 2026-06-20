package com.banco.basico.simulador.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Carteira {
    int id;
    float saldo;
    Usuario usuario;
}
