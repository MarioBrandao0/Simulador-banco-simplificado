package com.banco.basico.simulador.services;

import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoUsuario;
import com.banco.basico.simulador.exceptions.CpfExistenteException;
import com.banco.basico.simulador.exceptions.EmailExistenteException;
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


    private void emailExiste(Usuario usuario) {
        Optional<Usuario> usuarioPorEmail = repositorioUsuario.buscarUsuarioPorEmail(usuario.getEmail());

        if (usuarioPorEmail.isPresent()) {
            throw new EmailExistenteException("Email existente");
        }
    }

    private void cpfExiste(Usuario usuario) {
        Optional<Usuario> usuarioPorCpf = repositorioUsuario.buscarUsuarioPorCpf(usuario.getCpf());
        if (usuarioPorCpf.isPresent()) {
            throw new CpfExistenteException("CPF existente");
        }
    }

    public void salvarUsuario(DtoUsuario dtoUsuario) {
        Usuario novoUsuario = new Usuario(dtoUsuario.cpf(), dtoUsuario.nome(), dtoUsuario.email(), dtoUsuario.senha(), dtoUsuario.tipoUsuario());
        emailExiste(novoUsuario);
        cpfExiste(novoUsuario);

        repositorioUsuario.salvarUsuario(novoUsuario);
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(repositorioUsuario.listarUsuarios());
    }
}
