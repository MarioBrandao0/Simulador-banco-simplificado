package com.banco.basico.simulador.exceptions;

public class TransferenciaNaoAutorizada extends RuntimeException {
    public TransferenciaNaoAutorizada(String message) {
        super(message);
    }
}
