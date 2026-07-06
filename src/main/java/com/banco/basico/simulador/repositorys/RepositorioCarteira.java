package com.banco.basico.simulador.repositorys;

import com.banco.basico.simulador.domain.Carteira;
import com.banco.basico.simulador.exceptions.CarteiraNaoEncontradaException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class RepositorioCarteira {

    private final Map<UUID, Carteira> carteiras = new ConcurrentHashMap<>();

    public void salvarCarteira(Carteira carteira) {
        carteiras.put(carteira.getId(), carteira);
    }

    public Optional<Carteira> buscarCarteira(UUID idCarteira) {
        return Optional.ofNullable(carteiras.get(idCarteira));
    }

    public BigDecimal buscarSaldo(UUID idCarteira) {
        return carteiras.get(idCarteira).getSaldo();
    }

    public List<Carteira> listarTodas() {
        return new ArrayList<>(carteiras.values());
    }
}
