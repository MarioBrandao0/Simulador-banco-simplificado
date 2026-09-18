package com.banco.basico.simulador.transferencia.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record DtoTransacao(
        @NotBlank(message = "A chave pix não pode estar vazia")
        String chavePixDestinatario,
        @Positive(message = "Digite um valor acima de 0")
        BigDecimal valor
) {

}
