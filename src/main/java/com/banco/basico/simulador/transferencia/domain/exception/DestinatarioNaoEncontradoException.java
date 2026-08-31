package com.banco.basico.simulador.transferencia.domain.exception;

public class DestinatarioNaoEncontradoException extends RuntimeException {
    public DestinatarioNaoEncontradoException(String message) {
        super(message);
    }
}
