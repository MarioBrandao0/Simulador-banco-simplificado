package com.banco.basico.simulador.exceptions;

public class CpfExistenteException extends RuntimeException {
    public CpfExistenteException(String message) {
        super(message);
    }
}
