package com.banco.basico.simulador.autenticacao.api.dto;

import com.banco.basico.simulador.usuario.domain.TipoUsuario;

import java.util.UUID;

public record DtoResponseLogin(
        String token,
        UUID id,
        String nome,
        String email,
        TipoUsuario tipo
) {
}
