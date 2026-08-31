package com.banco.basico.simulador.transferencia.api;

import com.banco.basico.simulador.transferencia.api.dto.DtoTransacao;
import com.banco.basico.simulador.autenticacao.infrastructure.security.UsuarioAutenticado;
import com.banco.basico.simulador.transferencia.application.ServiceTransacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
