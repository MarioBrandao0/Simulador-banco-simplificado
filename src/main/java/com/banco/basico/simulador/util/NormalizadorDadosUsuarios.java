package com.banco.basico.simulador.util;

import org.springframework.stereotype.Component;

@Component
public class NormalizadorDadosUsuarios {
    public String normalizarCpf(String cpf) {
        return somenteNumeros(cpf);
    }

    public String normalizarEmail(String email) {
        return email == null
                ? null
                : email.trim().toLowerCase();
    }

    private String somenteNumeros(String valor) {
        return valor == null
                ? null
                : valor.replaceAll("\\D", "");
    }
}
