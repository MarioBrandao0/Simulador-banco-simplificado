package com.banco.basico.simulador.controllers;


import com.banco.basico.simulador.services.ServiceCarteira;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/carteira")
public class ControllerCarteira {

    ServiceCarteira serviceCarteira;

    @GetMapping("/saldo/{id}")
    public ResponseEntity<?> consultarSaldo(@PathVariable UUID id, Authentication authentication) {
        String emailVerificado = authentication.getName();
        return ResponseEntity.ok().body(serviceCarteira.consultarSaldo(id, emailVerificado));
    }
}
