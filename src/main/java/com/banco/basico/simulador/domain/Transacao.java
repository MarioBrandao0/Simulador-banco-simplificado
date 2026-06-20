package com.banco.basico.simulador.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class Transacao {
    UUID id;
    float valor;
    Usuario remetente;
    Usuario destinatario;
    LocalDate data;
    LocalTime hora;

    public Transacao(float valor, Usuario remetente, Usuario destinatario, LocalDate data, LocalTime hora) {
        this.id = UUID.randomUUID();
        this.valor = valor;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.data = data;
        this.hora = hora;
    }
}
