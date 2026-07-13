package com.banco.basico.simulador.exceptions;

public class ServicoIndisponivelException extends RuntimeException {
    public ServicoIndisponivelException(String message) {
        super(message);
    }
}
