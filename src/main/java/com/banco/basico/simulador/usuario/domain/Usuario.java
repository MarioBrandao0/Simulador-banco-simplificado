package com.banco.basico.simulador.usuario.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false, unique = true)
    String cpf;
    @Column(nullable = false)
    String nome;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false)
    String senha;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TipoUsuario tipoUsuario;

    public Usuario(String cpf, String nome, String email, String senha, TipoUsuario tipoUsuario) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
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
