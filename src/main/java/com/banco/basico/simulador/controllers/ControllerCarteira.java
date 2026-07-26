package com.banco.basico.simulador.controllers;

import com.banco.basico.simulador.dto.DtoResponseSaldo;
import com.banco.basico.simulador.services.ServiceCarteira;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/carteira")
public class ControllerCarteira {

    ServiceCarteira serviceCarteira;

    @GetMapping("/{id}/saldo")
    public ResponseEntity<?> consultarSaldo(@PathVariable UUID id){
        return ResponseEntity.ok().body(serviceCarteira.consultarSaldo(id));
    }
}
