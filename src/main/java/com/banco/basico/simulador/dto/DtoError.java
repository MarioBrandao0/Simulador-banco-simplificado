package com.banco.basico.simulador.dto;

import org.springframework.http.HttpStatus;

public record DtoError(
        HttpStatus httpStatus,
        String message
) {
}
