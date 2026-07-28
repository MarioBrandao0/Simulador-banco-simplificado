package com.banco.basico.simulador.exceptions;

import com.banco.basico.simulador.dto.DtoError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.banco.basico.simulador.dto.DtoError;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionsHandle {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<DtoError> handleAccessDeniedException(AccessDeniedException ex) {
        DtoError dtoError = new DtoError(HttpStatus.FORBIDDEN, ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dtoError);
    }
}
