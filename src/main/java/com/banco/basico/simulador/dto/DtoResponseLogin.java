package com.banco.basico.simulador.dto;

import com.banco.basico.simulador.enums.TipoUsuario;

import java.util.UUID;

public record DtoResponseLogin(
        String token,
        UUID id,
        String nome,
        String email,
        TipoUsuario tipo
) {
}
