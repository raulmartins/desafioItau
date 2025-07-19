package com.desafio.itau.demo.service;

import com.desafio.itau.demo.model.PasswordValidationRequest;
import com.desafio.itau.demo.model.PasswordValidationResponse;
import com.desafio.itau.demo.validator.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementação do service de validação de senhas
 * Orquestra a validação delegando para o validador específico
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordValidationServiceImpl implements PasswordValidationService {
    
    private final PasswordValidator passwordValidator;
    
    @Override
    public PasswordValidationResponse validatePassword(PasswordValidationRequest request) {
        log.info("Iniciando validação de senha");
        
        try {
            PasswordValidationResponse response = passwordValidator.validate(request.getPassword());
            
            if (response.isValid()) {
                log.info("Senha validada com sucesso");
            } else {
                log.warn("Senha inválida: {}", response.getMessage());
            }
            
            return response;
            
        } catch (Exception e) {
            log.error("Erro durante a validação da senha", e);
            return PasswordValidationResponse.invalid("Erro interno durante a validação");
        }
    }
} 