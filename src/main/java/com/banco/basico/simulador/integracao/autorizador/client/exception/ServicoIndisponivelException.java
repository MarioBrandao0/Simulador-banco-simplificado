package com.banco.basico.simulador.integracao.autorizador.client.exception;

public class ServicoIndisponivelException extends RuntimeException {
    public ServicoIndisponivelException(String message) {
        super(message);
    }
}
