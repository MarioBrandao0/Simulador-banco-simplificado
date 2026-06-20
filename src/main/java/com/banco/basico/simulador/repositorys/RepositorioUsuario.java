package com.banco.basico.simulador.repositorys;

import com.banco.basico.simulador.domain.Usuario;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RepositorioUsuario {

    private final Map<UUID, Usuario> usuarios = new ConcurrentHashMap<>();

    public void salvarUsuario(Usuario usuario) {
        this.usuarios.put(usuario.getId(), usuario);
    }

    public Usuario buscarUsuario(UUID id) {
        return this.usuarios.get(id);
    }

    public Optional<Usuario> buscarUsuarioPorCpf(String cpf) {
        return this.usuarios.values().stream().filter(u -> u.getCpf().equals(cpf)).findFirst();
    }

    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return this.usuarios.values().stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(this.usuarios.values());
    }
}
