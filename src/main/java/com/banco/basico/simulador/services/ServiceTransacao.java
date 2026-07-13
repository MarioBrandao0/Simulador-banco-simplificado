package com.banco.basico.simulador.services;

import com.banco.basico.simulador.clients.AutorizadorClient;
import com.banco.basico.simulador.domain.Transacao;
import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoTransacao;
import com.banco.basico.simulador.enums.TipoUsuario;
import com.banco.basico.simulador.exceptions.LojistaNaoPodeTransferirException;
import com.banco.basico.simulador.exceptions.TransferenciaNaoAutorizada;
import com.banco.basico.simulador.exceptions.TransferenciaParaSiMesmoException;
import com.banco.basico.simulador.repositorys.RepositorioTransacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ServiceTransacao {
    private final RepositorioTransacao repositorioTransacao;
    private final ServiceUsuario serviceUsuario;
    private final AutorizadorClient autorizadorClient;

    private final ServiceCarteira serviceCarteira;

    public void transacao(DtoTransacao dtoTransacao) {
        Usuario remetente = serviceUsuario.buscarUsuario(dtoTransacao.idRemetente()).get();

        if (remetente.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
            throw new LojistaNaoPodeTransferirException("Lojista não pode transferir dinheiro");
        }

        Usuario destinatario = serviceUsuario.buscarUsuarioPorCpf(dtoTransacao.chavePixDestinatario()).get();

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
