package com.desafio.itau.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de validação de senha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta da validação de senha")
public class PasswordValidationResponse {
    
    @Schema(
        description = "Indica se a senha é válida",
        example = "true"
    )
    private boolean valid;
    
    @Schema(
        description = "Mensagem descritiva do resultado da validação",
        example = "Senha válida"
    )
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