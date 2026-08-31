package com.banco.basico.simulador.usuario.api;

import com.banco.basico.simulador.usuario.api.dto.DtoResponseUsuario;
import com.banco.basico.simulador.usuario.api.dto.DtoUsuario;
import com.banco.basico.simulador.usuario.application.ServiceUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class ControllerUsuario {
    @Autowired
    ServiceUsuario serviceUsuario;

    @PostMapping("/criar")
    public ResponseEntity<?> criarUsuario(@RequestBody DtoUsuario dtoUsuario) {
        serviceUsuario.salvarUsuario(dtoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // Endpoint intencionalmente mantido sem autenticação para facilitar os testes da API.
    // Em um cenário real de produção, esta rota deve ser protegida por autenticação e autorização.
    @GetMapping("/listar")
    public ResponseEntity<?> listarTodos() {
        List<DtoResponseUsuario> listaDeUsuarios = serviceUsuario.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(listaDeUsuarios);
    }

}
