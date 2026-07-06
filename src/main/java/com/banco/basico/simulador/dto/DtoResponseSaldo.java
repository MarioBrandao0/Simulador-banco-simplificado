package com.banco.basico.simulador.dto;

import java.math.BigDecimal;

public record DtoResponseSaldo(
        String nomeUsuario,
        BigDecimal valor
) {
}
