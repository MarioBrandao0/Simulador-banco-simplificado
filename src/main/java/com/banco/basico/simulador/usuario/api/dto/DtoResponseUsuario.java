package com.banco.basico.simulador.usuario.api.dto;

import com.banco.basico.simulador.usuario.domain.Usuario;
import com.banco.basico.simulador.usuario.domain.TipoUsuario;

import java.util.UUID;

public record DtoResponseUsuario(
        UUID idUsuario,
        String nome,
        String cpf,
        String email,
        TipoUsuario tipo
) {
    public static DtoResponseUsuario converter(Usuario usuario) {
        return new DtoResponseUsuario(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getTipoUsuario()
        );
    }
}
