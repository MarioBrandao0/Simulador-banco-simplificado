package com.banco.basico.simulador.repositorys;

import com.banco.basico.simulador.domain.Carteira;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepositoryCarteira extends JpaRepository<Carteira, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u.carteira FROM Usuario u WHERE u.id = :idUsuario")
    Optional<Carteira> buscarPorUsuarioIdComLock(@Param("idUsuario") UUID idUsuario);

    @Query("SELECT u.carteira FROM Usuario u WHERE u.id = :idUsuario")
    public Optional<Carteira> encontrarCarteiraPorIdUsuario(UUID idUsuario);
}
