package com.banco.basico.simulador.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/mock")
public class ControllerMockAutorizador {

    @GetMapping("/authorize")
    public ResponseEntity<Map<String, Object>> autorizar() {
        Map<String, Object> data = Map.of("authorization", true);
        Map<String, Object> resposta = Map.of(
                "status", "success",
                "data", data
        );
        return ResponseEntity.ok(resposta);
    }
}