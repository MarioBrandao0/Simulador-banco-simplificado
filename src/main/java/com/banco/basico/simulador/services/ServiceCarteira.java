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
    Carteira carteira  = buscarCarteira(idUsuario);
    carteira.depositar(valor);
  }

  @Transactional
  public void sacar(UUID idUsuario, BigDecimal valor) {
    Carteira carteira = buscarCarteira(idUsuario);
    carteira.sacar(valor);
  }
}
