package com.banco.basico.simulador.usuario.domain.exception;

public class CpfExistenteException extends RuntimeException {
    public CpfExistenteException(String message) {
        super(message);
    }
}
