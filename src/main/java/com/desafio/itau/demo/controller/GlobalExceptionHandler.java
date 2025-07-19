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
     * Trata erros de JSON inválido
     */
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<PasswordValidationResponse> handleHttpMessageNotReadableException(
            org.springframework.http.converter.HttpMessageNotReadableException ex) {
        log.warn("JSON inválido recebido: {}", ex.getMessage());
        
        return ResponseEntity.badRequest()
            .body(PasswordValidationResponse.invalid("JSON inválido"));
    }
    
    /**
     * Trata erros de tipo de mídia não suportado
     */
    @ExceptionHandler(org.springframework.web.HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<PasswordValidationResponse> handleHttpMediaTypeNotSupportedException(
            org.springframework.web.HttpMediaTypeNotSupportedException ex) {
        log.warn("Tipo de mídia não suportado: {}", ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body(PasswordValidationResponse.invalid("Tipo de mídia não suportado"));
    }
    
    /**
     * Trata exceções genéricas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<PasswordValidationResponse> handleGenericException(Exception ex) {
        // Não captura exceções relacionadas ao OpenAPI/Swagger
        if (ex.getClass().getName().contains("springdoc") || 
            ex.getClass().getName().contains("swagger") ||
            ex.getClass().getName().contains("openapi") ||
            ex.getClass().getName().contains("ControllerAdviceBean")) {
            throw new RuntimeException(ex);
        }
        
        log.error("Erro interno da aplicação", ex);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(PasswordValidationResponse.invalid("Erro interno do servidor"));
    }
} 