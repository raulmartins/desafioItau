package com.desafio.itau.demo.controller;

import com.desafio.itau.demo.model.PasswordValidationRequest;
import com.desafio.itau.demo.model.PasswordValidationResponse;
import com.desafio.itau.demo.service.PasswordValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Validação de Senhas", description = "APIs para validação de senhas seguindo regras de segurança")
public class PasswordValidationController {
    
    private final PasswordValidationService passwordValidationService;
    
    /**
     * Valida se uma senha atende aos critérios de segurança
     * 
     * @param request requisição contendo a senha a ser validada
     * @return ResponseEntity com o resultado da validação
     */
    @PostMapping("/validate")
    @Operation(
        summary = "Validar senha",
        description = "Valida se uma senha atende aos critérios de segurança estabelecidos. " +
                     "A senha deve ter pelo menos 9 caracteres, conter dígitos, letras minúsculas, " +
                     "maiúsculas, caracteres especiais e não possuir caracteres repetidos ou espaços."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Senha validada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PasswordValidationResponse.class),
                examples = {
                    @ExampleObject(
                        name = "Senha Válida",
                        value = "{\"valid\": true, \"message\": \"Senha válida\"}"
                    ),
                    @ExampleObject(
                        name = "Senha Inválida",
                        value = "{\"valid\": false, \"message\": \"Senha deve ter pelo menos 9 caracteres\"}"
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados de entrada inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @ExampleObject(
                        name = "Senha Vazia",
                        value = "{\"timestamp\": \"2025-07-19T00:00:00\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"A senha não pode estar vazia\"}"
                    )
                }
            )
        )
    })
    public ResponseEntity<PasswordValidationResponse> validatePassword(
            @Valid @RequestBody PasswordValidationRequest request) {
        
        log.info("Recebida requisição para validação de senha");
        
        PasswordValidationResponse response = passwordValidationService.validatePassword(request);
        
        return ResponseEntity.ok(response);
    }
} 