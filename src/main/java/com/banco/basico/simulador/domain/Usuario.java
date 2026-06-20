package com.banco.basico.simulador.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {
    int  id;
    String cpf;
    String nome;
    String email;
    String senha;
}
