package com.desafio.itau.demo.validator;

import com.desafio.itau.demo.model.PasswordValidationResponse;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementação do validador de senhas
 * Implementa todas as regras de validação especificadas no desafio
 */
@Component
public class PasswordValidatorImpl implements PasswordValidator {
    
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-+";
    private static final int MIN_LENGTH = 9;
    
    @Override
    public PasswordValidationResponse validate(String password) {
        // Validação de entrada
        if (password == null || password.trim().isEmpty()) {
            return PasswordValidationResponse.invalid("Senha não pode estar vazia");
        }
        
        // Verifica se contém espaços em branco
        if (containsWhitespace(password)) {
            return PasswordValidationResponse.invalid("Senha não pode conter espaços em branco");
        }
        
        // Verifica comprimento mínimo
        if (password.length() < MIN_LENGTH) {
            return PasswordValidationResponse.invalid(
                String.format("Senha deve ter pelo menos %d caracteres", MIN_LENGTH)
            );
        }
        
        // Verifica se contém dígitos
        if (!containsDigit(password)) {
            return PasswordValidationResponse.invalid("Senha deve conter pelo menos 1 dígito");
        }
        
        // Verifica se contém letra minúscula
        if (!containsLowerCase(password)) {
            return PasswordValidationResponse.invalid("Senha deve conter pelo menos 1 letra minúscula");
        }
        
        // Verifica se contém letra maiúscula
        if (!containsUpperCase(password)) {
            return PasswordValidationResponse.invalid("Senha deve conter pelo menos 1 letra maiúscula");
        }
        
        // Verifica se contém caractere especial
        if (!containsSpecialCharacter(password)) {
            return PasswordValidationResponse.invalid("Senha deve conter pelo menos 1 caractere especial");
        }
        
        // Verifica se não contém caracteres repetidos
        if (hasRepeatedCharacters(password)) {
            return PasswordValidationResponse.invalid("Senha não pode conter caracteres repetidos");
        }
        
        return PasswordValidationResponse.valid("Senha válida");
    }
    
    /**
     * Verifica se a string contém espaços em branco
     */
    private boolean containsWhitespace(String password) {
        return password.chars().anyMatch(Character::isWhitespace);
    }
    
    /**
     * Verifica se a string contém pelo menos um dígito
     */
    private boolean containsDigit(String password) {
        return password.chars().anyMatch(Character::isDigit);
    }
    
    /**
     * Verifica se a string contém pelo menos uma letra minúscula
     */
    private boolean containsLowerCase(String password) {
        return password.chars().anyMatch(Character::isLowerCase);
    }
    
    /**
     * Verifica se a string contém pelo menos uma letra maiúscula
     */
    private boolean containsUpperCase(String password) {
        return password.chars().anyMatch(Character::isUpperCase);
    }
    
    /**
     * Verifica se a string contém pelo menos um caractere especial
     */
    private boolean containsSpecialCharacter(String password) {
        return password.chars()
            .mapToObj(ch -> (char) ch)
            .anyMatch(SPECIAL_CHARACTERS::contains);
    }
    
    /**
     * Verifica se a string contém caracteres repetidos
     */
    private boolean hasRepeatedCharacters(String password) {
        Set<Character> characters = new HashSet<>();
        return password.chars()
            .mapToObj(ch -> (char) ch)
            .anyMatch(ch -> !characters.add(ch));
    }
} 