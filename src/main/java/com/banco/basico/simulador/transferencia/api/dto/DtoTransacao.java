package com.banco.basico.simulador.transferencia.api.dto;

import java.math.BigDecimal;

public record DtoTransacao(
        String chavePixDestinatario,
        BigDecimal valor
) {
}
