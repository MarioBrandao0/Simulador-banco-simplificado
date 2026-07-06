package com.banco.basico.simulador.services;

import com.banco.basico.simulador.domain.Carteira;
import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoResponseSaldo;
import com.banco.basico.simulador.exceptions.CarteiraNaoEncontradaException;
import com.banco.basico.simulador.exceptions.SaldoInsuficienteException;
import com.banco.basico.simulador.exceptions.UsuarioNaoEncontradoException;
import com.banco.basico.simulador.repositorys.RepositorioCarteira;
import com.banco.basico.simulador.repositorys.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceCarteira {

    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioCarteira repositorioCarteira;

    public DtoResponseSaldo consultarSaldo(UUID idUsuario) {
        Optional<Usuario> usuario = Optional.ofNullable(repositorioUsuario.buscarUsuario(idUsuario).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado")));

        Carteira carteira = repositorioCarteira.buscarCarteira(usuario.get().getCarteira().getId())
                .orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));

        return new DtoResponseSaldo(usuario.get().getNome(), carteira.getSaldo());
    }

    public void depositar(UUID idUsuario, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        Carteira carteira = repositorioCarteira.buscarCarteira(idUsuario)
                .orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));

        carteira.setSaldo(carteira.getSaldo().add(valor));
    }

    public void sacar(UUID idUsuario, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        Carteira carteira = repositorioCarteira.buscarCarteira(idUsuario)
                .orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));

        if (carteira.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        carteira.setSaldo(carteira.getSaldo().subtract(valor));
    }
}
