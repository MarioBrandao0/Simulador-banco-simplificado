package com.banco.basico.simulador.services;

import com.banco.basico.simulador.clients.AutorizadorClient;
import com.banco.basico.simulador.domain.Transacao;
import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoTransacao;
import com.banco.basico.simulador.enums.TipoUsuario;
import com.banco.basico.simulador.exceptions.*;
import com.banco.basico.simulador.repositorys.RepositorioTransacao;
import com.banco.basico.simulador.repositorys.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceTransacao {
    private final RepositorioTransacao repositorioTransacao;
    private final RepositorioUsuario repositorioUsuario;
    private final ServiceUsuario serviceUsuario;
    private final AutorizadorClient autorizadorClient;

    private final ServiceCarteira serviceCarteira;


    private Usuario buscarRemetente(UUID idRemetente) {
        return Optional.ofNullable(repositorioUsuario.buscarUsuario(idRemetente)).orElseThrow(() -> new RemetenteNaoEncontradoException("Remetente não encontrado")).get();
    }

    //No momento só busca por cpf
    private Usuario buscarDestinatario(String chavePixDestinatario) {
        return Optional.ofNullable(repositorioUsuario.buscarUsuarioPorCpf(chavePixDestinatario)).orElseThrow(() -> new DestinatarioNaoEncontradoException("Destinatário não encontrado com essa chave pix")).get();
    }

    public void transacao(DtoTransacao dtoTransacao) {
        Usuario remetente = buscarRemetente(dtoTransacao.idRemetente());

        if (remetente.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
            throw new LojistaNaoPodeTransferirException("Lojista não pode transferir dinheiro");
        }

        Usuario destinatario = buscarDestinatario(dtoTransacao.chavePixDestinatario());

        if (remetente.getId().equals(destinatario.getId())) {
            throw new TransferenciaParaSiMesmoException("Não pode transferir dinheiro para si mesmo");
        }

        boolean autorizado = autorizadorClient.autorizar();

        if (!autorizado) {
            throw new TransferenciaNaoAutorizada("Transferencia não autorizada");
        }


        LocalDate data = LocalDate.now();
        LocalTime hora = LocalTime.now();

        serviceCarteira.sacar(remetente.getId(), dtoTransacao.valor());
        serviceCarteira.depositar(destinatario.getId(), dtoTransacao.valor());

        Transacao novaTransacao = new Transacao(
                dtoTransacao.valor(),
                remetente.getId(),
                destinatario.getId(),
                LocalDate.now(),
                LocalTime.now()
        );

        repositorioTransacao.salvar(novaTransacao);
    }
}
