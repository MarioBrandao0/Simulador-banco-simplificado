package com.banco.basico.simulador.controllers;

import com.banco.basico.simulador.domain.Usuario;
import com.banco.basico.simulador.dto.DtoUsuario;
import com.banco.basico.simulador.services.ServiceUsuario;
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

    @GetMapping("/listar")
    public ResponseEntity<?> listarTodos() {
        List<Usuario> listaDeUsuarios = serviceUsuario.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(listaDeUsuarios);
    }

}
