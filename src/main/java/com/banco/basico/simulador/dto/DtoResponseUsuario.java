package com.banco.basico.simulador.dto;

import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.enums.TipoUsuario;

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
