package com.banco.basico.simulador.dto;

import com.banco.basico.simulador.enums.TipoChavePix;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DtoChavePix(
        @NotNull
        TipoChavePix tipoChavePix,

        @NotBlank(message = "Informe uma chave pix")
        String valor
) {
    @AssertTrue(message = "A chave pix não corresponde ao tipo informado")
    public boolean chaveValida() {
        //Aqui fica meio confuso já que retornamos true mesmo sem ter valor ou tipo informado
        //Vale lembrar que esse metodo apenas verifica se o tipo bate com o valor informado.
        //Eles estarem em branco é tratado pela anotação que colocamos lá em cima
        //Nota mental para eu não esquecer :)
        if (tipoChavePix == null || valor == null || valor.isBlank()) {
            return true;
        }

        return switch (tipoChavePix) {
            case CPF -> validarCpf(valor);
            case EMAIL -> validarEmail(valor);
        };

    }
    private boolean validarCpf(String cpf) {
        return cpf.matches("\\d{11}");
    }

    private boolean validarEmail(String email) {
        return email.matches("^[\\\\w.+-]+@[\\\\w.-]+\\\\.[A-Za-z]{2,}$");
    }

}
