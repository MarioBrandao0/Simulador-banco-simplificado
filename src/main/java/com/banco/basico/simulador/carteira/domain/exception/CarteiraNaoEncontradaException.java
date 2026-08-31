package com.banco.basico.simulador.carteira.domain.exception;

public class CarteiraNaoEncontradaException extends RuntimeException {
    public CarteiraNaoEncontradaException(String message) {
        super(message);
    }
}
