package com.desafio.itau.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de validação de senha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordValidationResponse {
    
    private boolean valid;
    private String message;
    
    public static PasswordValidationResponse valid(String message) {
        return new PasswordValidationResponse(true, message);
    }
    
    public static PasswordValidationResponse invalid(String message) {
        return new PasswordValidationResponse(false, message);
    }
    
    public boolean isValid() {
        return valid;
    }
} 