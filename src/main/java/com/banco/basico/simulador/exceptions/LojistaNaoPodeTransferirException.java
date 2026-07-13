package com.banco.basico.simulador.exceptions;

public class LojistaNaoPodeTransferirException extends RuntimeException {
    public LojistaNaoPodeTransferirException(String message) {
        super(message);
    }
}
