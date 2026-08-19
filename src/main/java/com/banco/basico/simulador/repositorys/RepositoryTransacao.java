package com.banco.basico.simulador.repositorys;

import com.banco.basico.simulador.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositoryTransacao extends JpaRepository<Transacao, UUID> {
    List<Transacao> findByRemetente_Id(UUID remetenteId);

    List<Transacao> findByRemetente_IdAndDestinatario_Id(UUID remetenteId);

    List<Transacao> findByRemetente_IdOrDestinatario_Id(UUID remetenteId,  UUID destinatarioId);
}
