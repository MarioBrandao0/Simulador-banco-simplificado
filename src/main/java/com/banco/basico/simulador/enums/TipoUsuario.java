package com.banco.basico.simulador.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoUsuario {
    LOJISTA,
    CLIENTE;

    @JsonCreator
    public static TipoUsuario converter(String valor) {
        if  (valor == null || valor.isEmpty()) {
            throw new IllegalArgumentException("O tipo do cliente deve ser informado");
        }

        try{
            return TipoUsuario.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo do cliente inexistente" + valor);
        }

    }
}
