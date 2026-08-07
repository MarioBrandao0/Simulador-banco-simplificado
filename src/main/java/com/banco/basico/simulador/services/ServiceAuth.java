package com.banco.basico.simulador.services;

import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoLogin;
import com.banco.basico.simulador.dto.DtoResponseLogin;
import com.banco.basico.simulador.exceptions.UsuarioNaoEncontradoException;

import com.banco.basico.simulador.repositorys.RepositoryUsuario;
import com.banco.basico.simulador.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceAuth {
    private final AuthenticationManager authenticationManager;
    private final RepositoryUsuario repositorioUsuario;
    private final JwtService jwtService;


    public DtoResponseLogin login(DtoLogin dtoLogin) {
        //Isso aqui cria uma solicitação de autenticação não validada
        UsernamePasswordAuthenticationToken credenciais = UsernamePasswordAuthenticationToken.unauthenticated(
                dtoLogin.email(),
                dtoLogin.senha()
        );

        // Aqui ele já compara a senha(Vc não precisa fazer comparação manual)
        Authentication authentication = authenticationManager.authenticate(credenciais);

        // E ele so chega aqui se a senha e os dados baterem
        String emailAutenticado = authentication.getName();

        Usuario usuario = repositorioUsuario.findByEmail(emailAutenticado).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        String token = jwtService.gerarToken(emailAutenticado, usuario.getId(), usuario.getNome());

        return new DtoResponseLogin(token, usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipoUsuario());
    }
}
