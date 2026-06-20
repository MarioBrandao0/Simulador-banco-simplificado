package com.banco.basico.simulador.dto;

import com.banco.basico.simulador.enums.TipoUsuario;

public record DtoUsuario(
        String nome,
        String cpf,
        String email,
        String senha,
        TipoUsuario tipoUsuario

) {
}
