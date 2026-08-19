package com.banco.basico.simulador.services;

import com.banco.basico.simulador.clients.AutorizadorClient;
import com.banco.basico.simulador.domain.Carteira;
import com.banco.basico.simulador.domain.Transacao;
import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoResponseListarTransacoes;
import com.banco.basico.simulador.dto.DtoTransacao;
import com.banco.basico.simulador.exceptions.*;

import com.banco.basico.simulador.repositorys.RepositoryCarteira;
import com.banco.basico.simulador.repositorys.RepositoryTransacao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceTransacao {
    private final RepositoryTransacao repositorioTransacao;
    private final RepositoryCarteira repositoryCarteira;
    private final AutorizadorClient autorizadorClient;

    private final ServiceUsuario serviceUsuario;
    private final ServiceCarteira serviceCarteira;
    private final ServiceChavePix serviceChavePix;


    private Usuario buscarUsuarioPorId(UUID idRemetente) {
        return serviceUsuario.buscarUsuarioPorId(idRemetente);
    }

    private Usuario buscarUsuarioPorChavePix(String chavePixDestinatario) {
        return serviceChavePix.BuscarUsuarioPorChavePix(chavePixDestinatario);
    }


    public List<DtoResponseListarTransacoes> listarTransacoes(UUID idUsuario)  {
        List<Transacao> listaDeTransacoesDoUsuario = repositorioTransacao.findByRemetente_IdOrDestinatario_Id(idUsuario, idUsuario);

        List<DtoResponseListarTransacoes> dtoResponse = listaDeTransacoesDoUsuario.stream().map(t -> new DtoResponseListarTransacoes(
                t.getRemetente().getNome(),
                t.getValor(),
                t.getDestinatario().getNome(),
                t.getData(),
                t.getHora()
        )).toList();

        return dtoResponse;
    }

    @Transactional
    public void transacao(DtoTransacao dtoTransacao, UUID idRemetente) {
        Usuario remetente = buscarUsuarioPorId(idRemetente);

        Usuario destinatario = buscarUsuarioPorChavePix(dtoTransacao.chavePixDestinatario());

        if (remetente.getId().equals(destinatario.getId())) {
            throw new TransferenciaParaSiMesmoException("Não pode transferir dinheiro para si mesmo");
        }

        boolean autorizado = autorizadorClient.autorizar();

        if (!autorizado) {
            throw new TransferenciaNaoAutorizada("Transferencia não autorizada");
        }

        UUID primeiroId;
        UUID segundoId;

        if (remetente.getId().compareTo(idRemetente) < 0) {
            primeiroId = idRemetente;
            segundoId = destinatario.getId();
        } else {
            primeiroId = destinatario.getId();
            segundoId = idRemetente;
        }

        Carteira primeiraCarteira = repositoryCarteira.buscarPorUsuarioIdComLock(primeiroId).orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));
        Carteira segundaCarteira = repositoryCarteira.buscarPorUsuarioIdComLock(segundoId).orElseThrow(() -> new CarteiraNaoEncontradaException("Carteira não encontrada"));

        Carteira carteiraRemetente = primeiroId.equals(idRemetente) ?  primeiraCarteira : segundaCarteira;
        Carteira carteiraDestinatario = primeiroId.equals(destinatario.getId()) ?  primeiraCarteira : segundaCarteira;

        carteiraRemetente.sacar(dtoTransacao.valor());
        carteiraDestinatario.depositar(dtoTransacao.valor());

        Transacao novaTransacao = new Transacao(
                dtoTransacao.valor(),
                remetente,
                destinatario,
                LocalDate.now(),
                LocalTime.now()
        );

        repositorioTransacao.save(novaTransacao);
    }
}
