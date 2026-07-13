package com.banco.basico.simulador.dto;

public record DtoRespostaAutorizador(
        String status,
        DadosAutorizacao data
) {
    public record DadosAutorizacao(
            boolean authorization
    ) {}
}

