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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceCarteira {

    private final ServiceUsuario serviceUsuario;
    private final RepositorioCarteira repositorioCarteira;

    private Carteira buscarCarteira(UUID idUsuario) {
        Usuario usuario = serviceUsuario.buscarUsuarioPorId(idUsuario);
        Carteira carteira = repositorioCarteira.buscarCarteira(usuario.getCarteira().getId())
                .orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));

        return carteira;
    }


    @Cacheable(value = "saldos", key = "#idUsuario")
    public DtoResponseSaldo consultarSaldo(UUID idUsuario) {
        Usuario usuario = serviceUsuario.buscarUsuarioPorId(idUsuario);

        Carteira carteira = repositorioCarteira.buscarCarteira(usuario.getCarteira().getId())
                .orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));

        return new DtoResponseSaldo(usuario.getNome(), carteira.getSaldo());
    }

    @CacheEvict(value = "saldos", key = "#idUsuario")
    public void depositar(UUID idUsuario, BigDecimal valor) {

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        Carteira carteira = buscarCarteira(idUsuario);

        carteira.setSaldo(carteira.getSaldo().add(valor));
    }

    @CacheEvict(value = "saldos", key = "#idUsuario")
    public void sacar(UUID idUsuario, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        Carteira carteira = buscarCarteira(idUsuario);

        if (carteira.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        carteira.setSaldo(carteira.getSaldo().subtract(valor));
    }
}
