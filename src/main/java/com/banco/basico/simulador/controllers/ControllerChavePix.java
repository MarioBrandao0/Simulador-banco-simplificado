package com.banco.basico.simulador.controllers;

import com.banco.basico.simulador.dto.DtoChavePix;
import com.banco.basico.simulador.security.UsuarioAutenticado;
import com.banco.basico.simulador.services.ServiceChavePix;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chave-pix")
public class ControllerChavePix {
    @Autowired
    private ServiceChavePix serviceChavePix;

    @PostMapping()
    public ResponseEntity<?> cadastrarChavePix(@Valid @RequestBody DtoChavePix dtoChavePix, @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado) {
        serviceChavePix.CadastrarChavePix(dtoChavePix, usuarioAutenticado.id());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
