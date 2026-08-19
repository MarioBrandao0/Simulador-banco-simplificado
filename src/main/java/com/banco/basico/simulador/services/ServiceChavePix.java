package com.banco.basico.simulador.services;


import com.banco.basico.simulador.domain.ChavePix;
import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoChavePix;
import com.banco.basico.simulador.enums.TipoChavePix;
import com.banco.basico.simulador.exceptions.ChavePixNaoEncontrada;
import com.banco.basico.simulador.exceptions.ChavePixNaoPertenceAoUsuario;
import com.banco.basico.simulador.repositorys.RepositoryChavePix;
import com.banco.basico.simulador.repositorys.RepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceChavePix {

    @Autowired
    private RepositoryChavePix repositoryChavePix;
    @Autowired
    private ServiceUsuario serviceUsuario;

    public void CadastrarChavePix(DtoChavePix dtoChavePix, UUID idUsuario) {
        Usuario usuario = serviceUsuario.buscarUsuarioPorId(idUsuario);

        verificarChavePertenceAoUsuario(usuario, dtoChavePix.valor(), dtoChavePix.tipoChavePix());

        ChavePix novaChavePix = new ChavePix(dtoChavePix.valor(), usuario);
        repositoryChavePix.save(novaChavePix);
    }


    public Usuario BuscarUsuarioPorChavePix(String valor) {
        ChavePix chavePix = repositoryChavePix.findByValor(valor).orElseThrow(() -> new ChavePixNaoEncontrada("Chave pix não encontrada"));
        return chavePix.getUsuario();
    }

    private void verificarChavePertenceAoUsuario(Usuario usuario,String valor, TipoChavePix tipoChavePix) {
        boolean pertence = switch (tipoChavePix) {
            case CPF ->  cpfCorrespondente(usuario, valor);
            case EMAIL ->   emailCorrespondente(usuario, valor);
        };

        if (!pertence) {
            throw new ChavePixNaoPertenceAoUsuario("Chave pix informada não corresponde ao usuário");
        }
    }

    //Essa comparação precisa mudar, pois o que deve ser normalizado é o valor que chegar e não o que está no banco de dados
    private boolean cpfCorrespondente(Usuario usuario, String valor) {
        return usuario.getCpf().equals(valor);
    }

    private boolean emailCorrespondente(Usuario usuario, String valor) {
        return usuario.getEmail().equals(valor);
    }



}
