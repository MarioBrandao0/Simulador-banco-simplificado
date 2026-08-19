package com.banco.basico.simulador.repositorys;

import com.banco.basico.simulador.domain.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepositoryChavePix extends JpaRepository<ChavePix, UUID> {
    Optional<ChavePix> findByValor(String valor);
}
