package com.banco.basico.simulador.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record DtoTransacao(
        UUID idRemetente,
        String chavePixDestinatario,
        BigDecimal valor
) {
}
