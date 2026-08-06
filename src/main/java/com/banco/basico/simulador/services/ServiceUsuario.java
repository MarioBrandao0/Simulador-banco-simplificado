package com.banco.basico.simulador.services;

import com.banco.basico.simulador.domain.Carteira;
import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoResponseUsuario;
import com.banco.basico.simulador.dto.DtoUsuario;
import com.banco.basico.simulador.exceptions.CpfExistenteException;
import com.banco.basico.simulador.exceptions.EmailExistenteException;
import com.banco.basico.simulador.exceptions.UsuarioNaoEncontradoException;
import com.banco.basico.simulador.repositorys.RepositorioCarteira;
import com.banco.basico.simulador.repositorys.RepositorioUsuario;
import com.banco.basico.simulador.repositorys.RepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceUsuario {
    @Autowired
    RepositoryUsuario repositoryUsuario;

    @Autowired
    RepositorioCarteira repositorioCarteira;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RepositorioUsuario repositorioUsuario;

    public Usuario buscarUsuarioPorId(UUID idUsuario) {
        return (repositoryUsuario.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado")));

    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        return repositoryUsuario.findByCpf(cpf)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário com CPF não encontrado"));
    }


    private void emailExiste(String email) throws EmailExistenteException {
        Optional<Usuario> usuarioPorEmail = repositoryUsuario.findByEmail(email);

        if (usuarioPorEmail.isPresent()) {
            throw new EmailExistenteException("Email existente");
        }
    }

    private void cpfExiste(String cpf) throws CpfExistenteException {
        Optional<Usuario> usuarioPorCpf = repositoryUsuario.findByCpf(cpf);
        if (usuarioPorCpf.isPresent()) {
            throw new CpfExistenteException("CPF existente");
        }
    }

    @CacheEvict(value = "todosOsUsuarios", allEntries = true)
    public void salvarUsuario(DtoUsuario dtoUsuario) {
        emailExiste(dtoUsuario.email());
        cpfExiste(dtoUsuario.cpf());

        String senhaConvertida = passwordEncoder.encode(dtoUsuario.senha());

        Usuario novoUsuario = new Usuario(dtoUsuario.cpf(), dtoUsuario.nome(), dtoUsuario.email(), senhaConvertida, dtoUsuario.tipoUsuario());
        Carteira carteira = new Carteira();

        novoUsuario.setCarteira(carteira);
        repositoryUsuario.save(novoUsuario);

    }


    @Cacheable("todosOsUsuarios")
    public List<DtoResponseUsuario> listarTodos() {
        return new ArrayList<>(repositoryUsuario.findAll()).stream()
                .map(DtoResponseUsuario::converter).toList();
    }
}
