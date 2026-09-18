package com.banco.basico.simulador.autenticacao.infrastructure.ratelimit;

import com.banco.basico.simulador.autenticacao.infrastructure.security.UsuarioAutenticado;
import com.banco.basico.simulador.shared.web.dto.DtoError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Estudar isso aqui até entender de forma completa
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;
    private final ObjectMapper objectMapper;

    public RateLimitFilter(RateLimitService rateLimitService, ObjectMapper objectMapper) {
        this.rateLimitService = rateLimitService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsuarioAutenticado usuarioAutenticado = (UsuarioAutenticado) authentication.getPrincipal();

            if (usuarioAutenticado.id() == null) {
                throw new IllegalArgumentException("Usuário autenticado sem ID");
            }

            boolean permitido = rateLimitService.permitir(usuarioAutenticado.id());

            if (!permitido) {
                DtoError erro = new DtoError(HttpStatus.TOO_MANY_REQUESTS, "Você ja tentou varias vezes, aguarde um tempo");

                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(erro));

                return;
            }

            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
