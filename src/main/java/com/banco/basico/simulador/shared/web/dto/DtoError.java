package com.banco.basico.simulador.shared.web.dto;

import org.springframework.http.HttpStatus;

public record DtoError(
        HttpStatus httpStatus,
        String message
) {
}
