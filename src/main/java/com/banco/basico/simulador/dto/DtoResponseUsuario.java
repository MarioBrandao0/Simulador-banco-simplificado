package com.banco.basico.simulador.dto;

import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.enums.TipoUsuario;

public record DtoResponseUsuario(
        String nome,
        String cpf,
        String email,
        TipoUsuario tipo
) {
    public static DtoResponseUsuario converter(Usuario usuario) {
        return new DtoResponseUsuario(
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getTipoUsuario()
        );
    }
}
