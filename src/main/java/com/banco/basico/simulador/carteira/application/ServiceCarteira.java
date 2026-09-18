package com.banco.basico.simulador.carteira.application;

import com.banco.basico.simulador.carteira.domain.Carteira;
import com.banco.basico.simulador.carteira.api.dto.DtoResponseSaldo;
import com.banco.basico.simulador.carteira.domain.exception.CarteiraNaoEncontradaException;

import com.banco.basico.simulador.carteira.domain.exception.UsuarioComCarteiraException;
import com.banco.basico.simulador.carteira.infrastructure.persistence.RepositoryCarteira;
import com.banco.basico.simulador.shared.web.dto.ApiResponse;
import com.banco.basico.simulador.usuario.domain.Usuario;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceCarteira {

  private final RepositoryCarteira repositoryCarteira;

  private Carteira buscarCarteiraPorIdUsuario(UUID idUsuario) {
    Carteira carteira = repositoryCarteira.findByUsuario_Id((idUsuario))
            .orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));
    return carteira;
  }

  private void verificarCarteiraExistente(UUID idUsuario) {
    boolean usuario = repositoryCarteira.existsByUsuario_Id(idUsuario);
    if (usuario) {
      throw new UsuarioComCarteiraException("Usuario ja tem carteira");
    }
  }

  public void cadastrarCarteira(Usuario usuario) {
    verificarCarteiraExistente(usuario.getId());
    Carteira carteira = new Carteira(usuario);
    repositoryCarteira.save(carteira);
  }

  public ApiResponse<DtoResponseSaldo> consultarSaldo(UUID idUsuario) {
    Carteira carteira = buscarCarteiraPorIdUsuario(idUsuario);

    return new ApiResponse<>(HttpStatus.OK, new DtoResponseSaldo(carteira.getSaldo()));
  }

  @Transactional
  public void depositar(UUID idUsuario, BigDecimal valor) {
    Carteira carteira  = buscarCarteiraPorIdUsuario(idUsuario);
    carteira.depositar(valor);
  }

  @Transactional
  public void sacar(UUID idUsuario, BigDecimal valor) {
    Carteira carteira = buscarCarteiraPorIdUsuario(idUsuario);
    carteira.sacar(valor);
  }
}
