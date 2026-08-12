package com.banco.basico.simulador.exceptions;

import com.banco.basico.simulador.dto.DtoError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DtoError> handleAccessDeniedException(AccessDeniedException ex) {
        DtoError dtoError = new DtoError(HttpStatus.FORBIDDEN, ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dtoError);
    }

    @ExceptionHandler({
            CarteiraNaoEncontradaException.class,
            DestinatarioNaoEncontradoException.class,
            RemetenteNaoEncontradoException.class,
            UsuarioNaoEncontradoException.class

    })
    public ResponseEntity<DtoError> handleRecursoNaoEncontrado(RuntimeException e) {
        DtoError dtoError = new DtoError(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dtoError);
    }

    @ExceptionHandler({
            CpfExistenteException.class,
            EmailExistenteException.class
    })
    public ResponseEntity<DtoError> handleRecursoExistente(RuntimeException e) {
        DtoError dtoError = new DtoError(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(dtoError);
    }

    @ExceptionHandler(ServicoIndisponivelException.class)
    public ResponseEntity<DtoError> handleRecursoIndisponivel(RuntimeException e) {
        DtoError dtoError = new DtoError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dtoError);
    }
}
