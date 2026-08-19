package com.banco.basico.simulador.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "ChavesPix")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChavePix {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false, unique = true)
    private String valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public ChavePix(String chavePix, Usuario usuario) {
        this.valor = chavePix;
        this.usuario = usuario;
    }

}
