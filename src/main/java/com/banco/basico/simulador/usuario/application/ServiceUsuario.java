package com.banco.basico.simulador.usuario.application;

import com.banco.basico.simulador.carteira.application.ServiceCarteira;
import com.banco.basico.simulador.carteira.domain.Carteira;
import com.banco.basico.simulador.shared.web.dto.ApiResponse;
import com.banco.basico.simulador.usuario.domain.Usuario;
import com.banco.basico.simulador.usuario.api.dto.DtoResponseUsuario;
import com.banco.basico.simulador.usuario.api.dto.DtoUsuario;
import com.banco.basico.simulador.usuario.domain.exception.CpfExistenteException;
import com.banco.basico.simulador.usuario.domain.exception.EmailExistenteException;
import com.banco.basico.simulador.usuario.domain.exception.UsuarioNaoEncontradoException;
import com.banco.basico.simulador.usuario.infrastructure.persistence.RepositoryUsuario;
import com.banco.basico.simulador.usuario.application.NormalizadorDadosUsuarios;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceUsuario {
    @Autowired
    private RepositoryUsuario repositoryUsuario;

    @Autowired
    private NormalizadorDadosUsuarios normalizador;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ServiceCarteira serviceCarteira;

    @CacheEvict(value = "todosOsUsuarios", allEntries = true)
    @Transactional
    public void salvarUsuario(DtoUsuario dtoUsuario) {
        String emailNormalizado = normalizador.normalizarEmail(dtoUsuario.email());
        String cpfNormalizado = normalizador.normalizarCpf(dtoUsuario.cpf());

        emailExiste(emailNormalizado);
        cpfExiste(cpfNormalizado);

        String senhaConvertida = passwordEncoder.encode(dtoUsuario.senha());

        Usuario novoUsuario = new Usuario(
                cpfNormalizado,
                dtoUsuario.nome(),
                emailNormalizado,
                senhaConvertida,
                dtoUsuario.tipoUsuario()
        );

        serviceCarteira.cadastrarCarteira(novoUsuario);
        repositoryUsuario.save(novoUsuario);

    }


    @Cacheable("todosOsUsuarios")
    public ApiResponse<List<DtoResponseUsuario>> listarTodos() {
        List<DtoResponseUsuario> usuarios = repositoryUsuario.findAll()
                .stream()
                .map(DtoResponseUsuario::converter)
                .toList();

        return new ApiResponse<>(HttpStatus.OK, usuarios);
    }


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
}
