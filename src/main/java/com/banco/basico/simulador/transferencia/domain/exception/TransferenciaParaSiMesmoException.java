package com.banco.basico.simulador.transferencia.domain.exception;

public class TransferenciaParaSiMesmoException extends RuntimeException {
    public TransferenciaParaSiMesmoException(String message) {
        super(message);
    }
}
