package com.banco.basico.simulador.transferencia.api.dto;

import com.banco.basico.simulador.transferencia.domain.Transacao;

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
    public static DtoResponseListarTransacoes converter(Transacao t) {
        return new DtoResponseListarTransacoes(
                t.getRemetente().getNome(),
                t.getValor(),
                t.getDestinatario().getNome(),
                t.getData(),
                t.getHora()
        );
    }
}
