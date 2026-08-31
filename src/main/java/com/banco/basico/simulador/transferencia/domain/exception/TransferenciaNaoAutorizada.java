package com.banco.basico.simulador.transferencia.domain.exception;

public class TransferenciaNaoAutorizada extends RuntimeException {
    public TransferenciaNaoAutorizada(String message) {
        super(message);
    }
}
