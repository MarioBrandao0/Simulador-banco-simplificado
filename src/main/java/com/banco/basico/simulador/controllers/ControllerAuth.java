package com.banco.basico.simulador.controllers;

import com.banco.basico.simulador.dto.DtoLogin;
import com.banco.basico.simulador.dto.DtoResponseLogin;
import com.banco.basico.simulador.services.ServiceAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ControllerAuth {
    private final ServiceAuth serviceAuth;

    @PostMapping("/login")
    public ResponseEntity<DtoResponseLogin> login(@RequestBody DtoLogin dtoLogin) {
        return ResponseEntity.ok(serviceAuth.login(dtoLogin));
    }
}
