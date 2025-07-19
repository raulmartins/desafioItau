package com.desafio.itau.demo.validator;

import com.desafio.itau.demo.model.PasswordValidationResponse;

/**
 * Interface para validação de senhas
 * Segue o princípio de inversão de dependência (SOLID)
 */
public interface PasswordValidator {
    
    /**
     * Valida se uma senha atende aos critérios de segurança
     * 
     * @param password a senha a ser validada
     * @return PasswordValidationResponse com o resultado da validação
     */
    PasswordValidationResponse validate(String password);
} 