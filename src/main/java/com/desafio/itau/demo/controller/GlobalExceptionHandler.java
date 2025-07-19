package com.desafio.itau.demo.controller;

import com.desafio.itau.demo.model.PasswordValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler global para tratamento de exceções
 * Centraliza o tratamento de erros da aplicação
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Trata erros de validação de entrada
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PasswordValidationResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        String errorMessage = errors.values().stream().findFirst().orElse("Erro de validação");
        log.warn("Erro de validação: {}", errorMessage);
        
        return ResponseEntity.badRequest()
            .body(PasswordValidationResponse.invalid(errorMessage));
    }
    
    /**
     * Trata exceções genéricas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<PasswordValidationResponse> handleGenericException(Exception ex) {
        log.error("Erro interno da aplicação", ex);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(PasswordValidationResponse.invalid("Erro interno do servidor"));
    }
} 