package com.desafio.itau.demo.controller;

import com.desafio.itau.demo.model.PasswordValidationRequest;
import com.desafio.itau.demo.model.PasswordValidationResponse;
import com.desafio.itau.demo.service.PasswordValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST para validação de senhas
 * Expõe a API de validação seguindo as melhores práticas REST
 */
@Slf4j
@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordValidationController {
    
    private final PasswordValidationService passwordValidationService;
    
    /**
     * Valida se uma senha atende aos critérios de segurança
     * 
     * @param request requisição contendo a senha a ser validada
     * @return ResponseEntity com o resultado da validação
     */
    @PostMapping("/validate")
    public ResponseEntity<PasswordValidationResponse> validatePassword(
            @Valid @RequestBody PasswordValidationRequest request) {
        
        log.info("Recebida requisição para validação de senha");
        
        PasswordValidationResponse response = passwordValidationService.validatePassword(request);
        
        return ResponseEntity.ok(response);
    }
} 