package com.banco.basico.simulador.dto;

import org.hibernate.validator.constraints.br.CPF;

import com.banco.basico.simulador.enums.TipoUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DtoUsuario(
        @NotBlank(message = "O nome não pode estar vazio")
        @Size(min = 5, message = "Informe um nome válido")
        String nome,

        @NotBlank(message = "O CPF não pode estar vazio")
        @CPF(message = "informe um cpf válido")
        String cpf,

        @NotBlank(message = "O email não pode estar vazio")
        @Email(message = "Informe um email válido")
        String email,

        @NotBlank(message = "A senha não pode estar vazia")
        @Size(min = 5, message = "A senha deve ter pelo menos 5 caracteres")
        String senha,

        @NotBlank(message = "O tipo de usuário é obrigatorio")
        TipoUsuario tipoUsuario

) {
    public DtoUsuario {

    }
}
