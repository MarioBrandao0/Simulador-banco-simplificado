package com.banco.basico.simulador.controllers;

import com.banco.basico.simulador.dto.DtoTransacao;
import com.banco.basico.simulador.services.ServiceTransacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transacao")
public class ControllerTransacao {
    private final ServiceTransacao serviceTransacao;

    public ControllerTransacao(ServiceTransacao serviceTransacao) {
        this.serviceTransacao = serviceTransacao;
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody DtoTransacao dtoTransacao) {
        serviceTransacao.transacao(dtoTransacao);
        return ResponseEntity.status(HttpStatus.OK).body("Transacao realizada com sucesso");
    }
}
