package com.desafio.itau.demo.service;

import com.desafio.itau.demo.model.PasswordValidationRequest;
import com.desafio.itau.demo.model.PasswordValidationResponse;

/**
 * Interface do service de validação de senhas
 * Segue o princípio de inversão de dependência (SOLID)
 */
public interface PasswordValidationService {
    
    /**
     * Valida uma senha baseada na requisição
     * 
     * @param request requisição contendo a senha a ser validada
     * @return PasswordValidationResponse com o resultado da validação
     */
    PasswordValidationResponse validatePassword(PasswordValidationRequest request);
} 