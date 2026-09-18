package com.banco.basico.simulador.usuario.api;

import com.banco.basico.simulador.shared.web.dto.ApiResponse;
import com.banco.basico.simulador.usuario.api.dto.DtoResponseUsuario;
import com.banco.basico.simulador.usuario.api.dto.DtoUsuario;
import com.banco.basico.simulador.usuario.application.ServiceUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class ControllerUsuario {
    @Autowired
    ServiceUsuario serviceUsuario;

    @PostMapping()
    public ResponseEntity<?> criarUsuario(@RequestBody @Valid DtoUsuario dtoUsuario) {
        serviceUsuario.salvarUsuario(dtoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // Endpoint intencionalmente mantido sem autenticação para facilitar os testes da API.
    // Em um cenário real de produção, esta rota deve ser protegida por autenticação e autorização.
    @GetMapping()
    public ResponseEntity<?> listarTodos() {
        ApiResponse<List<DtoResponseUsuario>> listaDeUsuarios = serviceUsuario.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(listaDeUsuarios);
    }

}
