package com.desafio.itau.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de validação de senha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordValidationRequest {
    
    @NotBlank(message = "A senha não pode estar vazia")
    private String password;
} 