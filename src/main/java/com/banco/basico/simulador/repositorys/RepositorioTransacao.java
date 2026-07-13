package com.banco.basico.simulador.repositorys;

import com.banco.basico.simulador.domain.Transacao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RepositorioTransacao {
    private final Map<UUID, Transacao> transacoes = new ConcurrentHashMap<>();

    public void salvar(Transacao transacao) {
        transacoes.put(transacao.getId(), transacao);
    }

    public List<Transacao> ListarTransacoesUsuario(UUID idUsuario) {
        return transacoes.values().stream().filter(t -> t.getId().equals(idUsuario)).toList();
    }
}
