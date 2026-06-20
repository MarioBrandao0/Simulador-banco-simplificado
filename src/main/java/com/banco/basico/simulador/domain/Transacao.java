package com.banco.basico.simulador.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class Transacao {
    int id;
    float valor;
    Usuario remetente;
    Usuario destinatario;
    LocalDate data;
    LocalTime hora;
}
