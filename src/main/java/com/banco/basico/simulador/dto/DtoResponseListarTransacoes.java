package com.banco.basico.simulador.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

//Testar isso aqui
public record DtoResponseListarTransacoes(
    String remetente,
    BigDecimal valor,
    String destinatario,
    LocalDate data,
    LocalTime hora
) {
}
