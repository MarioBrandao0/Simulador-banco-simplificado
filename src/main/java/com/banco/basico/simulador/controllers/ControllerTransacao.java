package com.banco.basico.simulador.controllers;

import com.banco.basico.simulador.dto.DtoTransacao;
import com.banco.basico.simulador.security.UsuarioAutenticado;
import com.banco.basico.simulador.services.ServiceTransacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.UUID;

@RestController
@RequestMapping("/api/transacao")
public class ControllerTransacao {
    private final ServiceTransacao serviceTransacao;

    public ControllerTransacao(ServiceTransacao serviceTransacao) {
        this.serviceTransacao = serviceTransacao;
    }

    @GetMapping("/minhas")
    public ResponseEntity<?> listarTransacoes(@AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado) {
        return ResponseEntity.ok(serviceTransacao.listarTransacoes(usuarioAutenticado.id()));
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody DtoTransacao dtoTransacao, @AuthenticationPrincipal UsuarioAutenticado authentication) {
        serviceTransacao.transacao(dtoTransacao, authentication.id());
        return ResponseEntity.status(HttpStatus.OK).body("Transacao realizada com sucesso");
    }
}
