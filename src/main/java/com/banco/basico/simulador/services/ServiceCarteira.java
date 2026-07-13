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

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceCarteira {

    private final ServiceUsuario serviceUsuario;
    private final RepositorioCarteira repositorioCarteira;

    private Usuario buscarDonoDaCarteira(UUID idUsuario) {
        return serviceUsuario.buscarUsuario(idUsuario).get();
    }

    @Cacheable("saldos")
    public DtoResponseSaldo consultarSaldo(UUID idUsuario) {
        Optional<Usuario> usuario = serviceUsuario.buscarUsuario(idUsuario);

        Carteira carteira = repositorioCarteira.buscarCarteira(usuario.get().getCarteira().getId())
                .orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));

        return new DtoResponseSaldo(usuario.get().getNome(), carteira.getSaldo());
    }

    @CacheEvict("saldos")
    public void depositar(UUID idUsuario, BigDecimal valor) {
        Usuario donoDaCarteira = buscarDonoDaCarteira(idUsuario);
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        Carteira carteira = repositorioCarteira.buscarCarteira(donoDaCarteira.getCarteira().getId())
                .orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));

        carteira.setSaldo(carteira.getSaldo().add(valor));
    }

    @CacheEvict("saldos")
    public void sacar(UUID idUsuario, BigDecimal valor) {
        Usuario donoDaCarteira = buscarDonoDaCarteira(idUsuario);

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }

        Carteira carteira = repositorioCarteira.buscarCarteira(donoDaCarteira.getCarteira().getId())
                .orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));

        if (carteira.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        carteira.setSaldo(carteira.getSaldo().subtract(valor));
    }
}
