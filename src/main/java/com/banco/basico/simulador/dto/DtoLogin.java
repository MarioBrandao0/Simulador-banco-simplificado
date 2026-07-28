package com.banco.basico.simulador.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DtoLogin(
        @NotBlank(message = "O email é obrigatorio")
        @Email(message = "informe um email invalido")
        String email,

        @NotBlank(message = "A senha é obrigatoria")
        String senha
) {
}
