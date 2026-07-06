package com.banco.basico.simulador.services;

import com.banco.basico.simulador.domain.Carteira;
import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoResponseUsuario;
import com.banco.basico.simulador.dto.DtoUsuario;
import com.banco.basico.simulador.exceptions.CpfExistenteException;
import com.banco.basico.simulador.exceptions.EmailExistenteException;
import com.banco.basico.simulador.repositorys.RepositorioCarteira;
import com.banco.basico.simulador.repositorys.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceUsuario {
    @Autowired
    RepositorioUsuario repositorioUsuario;

    @Autowired
    RepositorioCarteira repositorioCarteira;


    private void emailExiste(String email) throws EmailExistenteException {
        Optional<Usuario> usuarioPorEmail = repositorioUsuario.buscarUsuarioPorEmail(email);

        if (usuarioPorEmail.isPresent()) {
            throw new EmailExistenteException("Email existente");
        }
    }

    private void cpfExiste(String cpf) throws CpfExistenteException {
        Optional<Usuario> usuarioPorCpf = repositorioUsuario.buscarUsuarioPorCpf(cpf);
        if (usuarioPorCpf.isPresent()) {
            throw new CpfExistenteException("CPF existente");
        }
    }

    public void salvarUsuario(DtoUsuario dtoUsuario) {
        emailExiste(dtoUsuario.email());
        cpfExiste(dtoUsuario.cpf());


        Usuario novoUsuario = new Usuario(dtoUsuario.cpf(), dtoUsuario.nome(), dtoUsuario.email(), dtoUsuario.senha(), dtoUsuario.tipoUsuario());
        Carteira carteira = new Carteira(novoUsuario);

       novoUsuario.setCarteira(carteira);
       repositorioUsuario.salvarUsuario(novoUsuario);
       repositorioCarteira.salvarCarteira(carteira);
    }


    public List<DtoResponseUsuario> listarTodos() {
        return new ArrayList<>(repositorioUsuario.listarUsuarios()).stream()
                .map(DtoResponseUsuario::converter).toList();
    }
}
