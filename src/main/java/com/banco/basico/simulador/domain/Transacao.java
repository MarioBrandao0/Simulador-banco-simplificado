package com.banco.basico.simulador.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "transacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_remetente")
    private Usuario remetente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_destinatario")
    private Usuario destinatario;

    @Column(nullable = false)
    LocalDate data;
    @Column(nullable = false)
    LocalTime hora;

    public Transacao(BigDecimal valor, Usuario remetente, Usuario destinatario, LocalDate data, LocalTime hora) {
        this.valor = valor;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.data = data;
        this.hora = hora;
    }
}
