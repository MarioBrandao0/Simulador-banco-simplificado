package com.banco.basico.simulador.transferencia.api.dto;

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
