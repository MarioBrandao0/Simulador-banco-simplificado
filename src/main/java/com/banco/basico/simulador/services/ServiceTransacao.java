package com.banco.basico.simulador.services;

import com.banco.basico.simulador.clients.AutorizadorClient;
import com.banco.basico.simulador.domain.Transacao;
import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoResponseListarTransacoes;
import com.banco.basico.simulador.dto.DtoTransacao;
import com.banco.basico.simulador.enums.TipoUsuario;
import com.banco.basico.simulador.exceptions.*;
import com.banco.basico.simulador.repositorys.RepositorioTransacao;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceTransacao {
    private final RepositorioTransacao repositorioTransacao;
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
        List<Transacao> listaDeTransacoesDoUsuario = repositorioTransacao.ListarTransacoesUsuario(idUsuario);

        List<DtoResponseListarTransacoes> dtoResponse = listaDeTransacoesDoUsuario.stream().map(t -> {
            Usuario remetente = buscarUsuarioPorId(t.getRemetente());
            Usuario destinatario = buscarUsuarioPorId(t.getDestinatario());

            return new DtoResponseListarTransacoes(
                    remetente.getNome(),
                    t.getValor(),
                    destinatario.getNome(),
                    t.getData(),
                    t.getHora()

            );
        }).toList();

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
                remetente.getId(),
                destinatario.getId(),
                LocalDate.now(),
                LocalTime.now()
        );

        repositorioTransacao.salvar(novaTransacao);
    }
}
