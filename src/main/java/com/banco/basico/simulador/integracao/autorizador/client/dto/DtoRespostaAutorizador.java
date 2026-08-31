package com.banco.basico.simulador.integracao.autorizador.client.dto;

public record DtoRespostaAutorizador(
        String status,
        DadosAutorizacao data
) {
    public record DadosAutorizacao(
            boolean authorization
    ) {}
}

