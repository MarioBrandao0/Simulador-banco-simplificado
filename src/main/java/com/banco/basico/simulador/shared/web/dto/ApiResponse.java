package com.banco.basico.simulador.shared.web.dto;

import org.springframework.http.HttpStatus;

public record ApiResponse<T> (
        HttpStatus status,
        T dados
) {
}
