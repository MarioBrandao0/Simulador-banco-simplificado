package com.banco.basico.simulador.repositorys;

import com.banco.basico.simulador.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepositoryUsuario extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByCpf(String cpf);

    Optional<Usuario> findByEmail(String email);
}
