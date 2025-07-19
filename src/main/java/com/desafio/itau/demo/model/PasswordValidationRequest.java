package com.desafio.itau.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Requisição para validação de senha")
public class PasswordValidationRequest {
    
    @NotBlank(message = "A senha não pode estar vazia")
    @Schema(
        description = "Senha a ser validada",
        example = "AbTp9!fok",
        minLength = 1
    )
    private String password;
} 