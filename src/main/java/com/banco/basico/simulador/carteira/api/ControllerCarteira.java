package com.banco.basico.simulador.carteira.api;


import com.banco.basico.simulador.autenticacao.infrastructure.security.UsuarioAutenticado;
import com.banco.basico.simulador.carteira.application.ServiceCarteira;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carteira")

public class ControllerCarteira {
    ServiceCarteira serviceCarteira;

    public ControllerCarteira(ServiceCarteira serviceCarteira) {
        this.serviceCarteira = serviceCarteira;
    }

    @GetMapping("/saldo")
    public ResponseEntity<?> consultarSaldo(@AuthenticationPrincipal UsuarioAutenticado authentication) {
        return ResponseEntity.ok().body(serviceCarteira.consultarSaldo(authentication.id()));
    }
}
