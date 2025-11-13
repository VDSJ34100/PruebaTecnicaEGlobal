package com.valerian.evaluacionplataformas.controller;

import com.valerian.evaluacionplataformas.exception.FirmaInvalidaException;
import com.valerian.evaluacionplataformas.exception.TransaccionNoEncontradaException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(FirmaInvalidaException.class)
    public ResponseEntity<?> handleFirma(FirmaInvalidaException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(TransaccionNoEncontradaException.class)
    public ResponseEntity<?> handleTransaccion(TransaccionNoEncontradaException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralErrors(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}

