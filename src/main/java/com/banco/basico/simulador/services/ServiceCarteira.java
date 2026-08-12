package com.banco.basico.simulador.services;

import com.banco.basico.simulador.domain.Carteira;
import com.banco.basico.simulador.dto.DtoResponseSaldo;
import com.banco.basico.simulador.exceptions.CarteiraNaoEncontradaException;
import com.banco.basico.simulador.exceptions.SaldoInsuficienteException;

import com.banco.basico.simulador.repositorys.RepositoryCarteira;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceCarteira {
  private final RepositoryCarteira repositoryCarteira;

  private Carteira buscarCarteira(UUID idUsuario) {
    Carteira carteira = repositoryCarteira.encontrarCarteiraPorIdUsuario(idUsuario)
        .orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));
    return carteira;
  }

  public DtoResponseSaldo consultarSaldo(UUID idUsuario) {
    Carteira carteira = buscarCarteira(idUsuario);

    return new DtoResponseSaldo(carteira.getSaldo());
  }

  @Transactional
  public void depositar(UUID idUsuario, BigDecimal valor) {

    if (valor.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Valor deve ser maior que zero");
    }

    Carteira carteira = buscarCarteira(idUsuario);

    carteira.setSaldo(carteira.getSaldo().add(valor));
  }

  @Transactional
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
