package com.banco.basico.simulador.domain;

import com.banco.basico.simulador.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Usuario {
    UUID id;
    String cpf;
    String nome;
    String email;
    String senha;
    TipoUsuario tipoUsuario;
    Carteira carteira;

    public Usuario(String cpf, String nome, String email, String senha, TipoUsuario tipoUsuario) {
        this.id = UUID.randomUUID();
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public void inserirCarteira(Carteira carteira){
        this.carteira = carteira;
    }

    @Override
    public String toString() {
        return (
                "Nome: " + nome + "\n" +
                "CPF: " + cpf + "\n" +
                "Email: " + email + "\n" +
                "Tipo: " + tipoUsuario + "\n"
        );
    }
}
