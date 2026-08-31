package com.banco.basico.simulador.transferencia.domain.exception;

public class LojistaNaoPodeTransferirException extends RuntimeException {
    public LojistaNaoPodeTransferirException(String message) {
        super(message);
    }
}
