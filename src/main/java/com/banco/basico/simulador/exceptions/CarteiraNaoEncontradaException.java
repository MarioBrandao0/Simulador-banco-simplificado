package com.banco.basico.simulador.exceptions;

public class CarteiraNaoEncontradaException extends RuntimeException {
    public CarteiraNaoEncontradaException(String message) {
        super(message);
    }
}
