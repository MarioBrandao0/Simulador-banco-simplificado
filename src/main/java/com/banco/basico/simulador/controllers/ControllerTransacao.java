package com.banco.basico.simulador.controllers;

import com.banco.basico.simulador.dto.DtoTransacao;
import com.banco.basico.simulador.services.ServiceTransacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> listarTransacoes(@PathVariable UUID id, Authentication authentication) {
        String emailVerificado = authentication.getName();
        return ResponseEntity.ok(serviceTransacao.listarTransacoes(id, emailVerificado));
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody DtoTransacao dtoTransacao) {
        serviceTransacao.transacao(dtoTransacao);
        return ResponseEntity.status(HttpStatus.OK).body("Transacao realizada com sucesso");
    }
}
