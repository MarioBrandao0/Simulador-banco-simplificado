package com.banco.basico.simulador.services;

import com.banco.basico.simulador.clients.AutorizadorClient;
import com.banco.basico.simulador.domain.Transacao;
import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoResponseListarTransacoes;
import com.banco.basico.simulador.dto.DtoTransacao;
import com.banco.basico.simulador.exceptions.*;

import com.banco.basico.simulador.repositorys.RepositoryTransacao;
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
    private final ServiceUsuario serviceUsuario;
    private final AutorizadorClient autorizadorClient;

    private final ServiceCarteira serviceCarteira;


    private Usuario buscarUsuarioPorId(UUID idRemetente) {
        return serviceUsuario.buscarUsuarioPorId(idRemetente);
    }

    //No momento só busca por cpf
    private Usuario buscarUsuarioPorChavePix(String chavePixDestinatario) {
        return serviceUsuario.buscarUsuarioPorCpf(chavePixDestinatario);
    }


    public List<DtoResponseListarTransacoes> listarTransacoes(UUID idUsuario)  {
        List<Transacao> listaDeTransacoesDoUsuario = repositorioTransacao.findByRemetente_Id(idUsuario);

        List<DtoResponseListarTransacoes> dtoResponse = listaDeTransacoesDoUsuario.stream().map(t -> new DtoResponseListarTransacoes(
                t.getRemetente().getNome(),
                t.getValor(),
                t.getDestinatario().getNome(),
                t.getData(),
                t.getHora()
        )).toList();

        return dtoResponse;
    }

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

        serviceCarteira.sacar(remetente.getId(), dtoTransacao.valor());
        serviceCarteira.depositar(destinatario.getId(), dtoTransacao.valor());

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
