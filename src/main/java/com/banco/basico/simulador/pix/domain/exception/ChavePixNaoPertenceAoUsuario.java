package com.banco.basico.simulador.pix.domain.exception;

public class ChavePixNaoPertenceAoUsuario extends RuntimeException {
    public ChavePixNaoPertenceAoUsuario(String message) {
        super(message);
    }
}
