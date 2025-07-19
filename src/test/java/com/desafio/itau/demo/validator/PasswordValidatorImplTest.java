package com.desafio.itau.demo.validator;

import com.desafio.itau.demo.model.PasswordValidationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes unitários para o PasswordValidatorImpl
 * Cobre todos os cenários de validação especificados no desafio
 */
@DisplayName("Password Validator Tests")
class PasswordValidatorImplTest {
    
    private PasswordValidatorImpl passwordValidator;
    
    @BeforeEach
    void setUp() {
        passwordValidator = new PasswordValidatorImpl();
    }
    
    @Test
    @DisplayName("Should return invalid for null password")
    void shouldReturnInvalidForNullPassword() {
        PasswordValidationResponse response = passwordValidator.validate(null);
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha não pode estar vazia");
    }
    
    @Test
    @DisplayName("Should return invalid for empty password")
    void shouldReturnInvalidForEmptyPassword() {
        PasswordValidationResponse response = passwordValidator.validate("");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha não pode estar vazia");
    }
    
    @Test
    @DisplayName("Should return invalid for password with only spaces")
    void shouldReturnInvalidForPasswordWithOnlySpaces() {
        PasswordValidationResponse response = passwordValidator.validate("   ");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha não pode estar vazia");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"a", "aa", "ab", "abc", "abcd", "abcde", "abcdef", "abcdefg", "abcdefgh"})
    @DisplayName("Should return invalid for passwords with less than 9 characters")
    void shouldReturnInvalidForPasswordsWithLessThanNineCharacters(String password) {
        PasswordValidationResponse response = passwordValidator.validate(password);
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha deve ter pelo menos 9 caracteres");
    }
    
    @Test
    @DisplayName("Should return invalid for password without digits")
    void shouldReturnInvalidForPasswordWithoutDigits() {
        PasswordValidationResponse response = passwordValidator.validate("Abcdefgh!");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha deve conter pelo menos 1 dígito");
    }
    
    @Test
    @DisplayName("Should return invalid for password without lowercase letters")
    void shouldReturnInvalidForPasswordWithoutLowercaseLetters() {
        PasswordValidationResponse response = passwordValidator.validate("ABCDEFGH1!");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha deve conter pelo menos 1 letra minúscula");
    }
    
    @Test
    @DisplayName("Should return invalid for password without uppercase letters")
    void shouldReturnInvalidForPasswordWithoutUppercaseLetters() {
        PasswordValidationResponse response = passwordValidator.validate("abcdefgh1!");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha deve conter pelo menos 1 letra maiúscula");
    }
    
    @Test
    @DisplayName("Should return invalid for password without special characters")
    void shouldReturnInvalidForPasswordWithoutSpecialCharacters() {
        PasswordValidationResponse response = passwordValidator.validate("Abcdefgh1");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha deve conter pelo menos 1 caractere especial");
    }
    
    @Test
    @DisplayName("Should return invalid for password with whitespace")
    void shouldReturnInvalidForPasswordWithWhitespace() {
        PasswordValidationResponse response = passwordValidator.validate("AbTp9 fok");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha não pode conter espaços em branco");
    }
    
    @Test
    @DisplayName("Should return invalid for password with tab character")
    void shouldReturnInvalidForPasswordWithTabCharacter() {
        PasswordValidationResponse response = passwordValidator.validate("AbTp9\tfok");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha não pode conter espaços em branco");
    }
    
    @Test
    @DisplayName("Should return invalid for password with newline character")
    void shouldReturnInvalidForPasswordWithNewlineCharacter() {
        PasswordValidationResponse response = passwordValidator.validate("AbTp9\nfok");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha não pode conter espaços em branco");
    }
    
    @Test
    @DisplayName("Should return invalid for password with repeated characters")
    void shouldReturnInvalidForPasswordWithRepeatedCharacters() {
        PasswordValidationResponse response = passwordValidator.validate("AbTp9!foo");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha não pode conter caracteres repetidos");
    }
    
    @Test
    @DisplayName("Should return invalid for password with repeated uppercase characters")
    void shouldReturnInvalidForPasswordWithRepeatedUppercaseCharacters() {
        PasswordValidationResponse response = passwordValidator.validate("AbTp9!foA");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha não pode conter caracteres repetidos");
    }
    
    @Test
    @DisplayName("Should return invalid for password with repeated digits")
    void shouldReturnInvalidForPasswordWithRepeatedDigits() {
        PasswordValidationResponse response = passwordValidator.validate("AbTp99!fok");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha não pode conter caracteres repetidos");
    }
    
    @Test
    @DisplayName("Should return invalid for password with repeated special characters")
    void shouldReturnInvalidForPasswordWithRepeatedSpecialCharacters() {
        PasswordValidationResponse response = passwordValidator.validate("AbTp9!!fok");
        
        assertThat(response.isValid()).isFalse();
        assertThat(response.getMessage()).isEqualTo("Senha não pode conter caracteres repetidos");
    }
    
    @Test
    @DisplayName("Should return valid for correct password")
    void shouldReturnValidForCorrectPassword() {
        PasswordValidationResponse response = passwordValidator.validate("AbTp9!fok");
        
        assertThat(response.isValid()).isTrue();
        assertThat(response.getMessage()).isEqualTo("Senha válida");
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "AbTp9!fok",
        "XyZ1@2#3$",
        "QwErTy1!2",
        "1aA!2bB@3",
        "ZxCvBnM1!"
    })
    @DisplayName("Should return valid for various correct passwords")
    void shouldReturnValidForVariousCorrectPasswords(String password) {
        PasswordValidationResponse response = passwordValidator.validate(password);
        
        assertThat(response.isValid()).isTrue()
            .withFailMessage("Password '%s' should be valid, but got: %s", password, response.getMessage());
        assertThat(response.getMessage()).isEqualTo("Senha válida");
    }
    
    @Test
    @DisplayName("Should validate all special characters")
    void shouldValidateAllSpecialCharacters() {
        String specialChars = "!@#$%^&*()-+";
        
        for (char specialChar : specialChars.toCharArray()) {
            String password = "AbTp9" + specialChar + "fok";
            PasswordValidationResponse response = passwordValidator.validate(password);
            
            assertThat(response.isValid()).isTrue()
                .withFailMessage("Password with special character '%c' should be valid", specialChar);
        }
    }
    
    @Test
    @DisplayName("Should handle case sensitivity correctly")
    void shouldHandleCaseSensitivityCorrectly() {
        // Test with mixed case that should be valid
        PasswordValidationResponse validResponse = passwordValidator.validate("AbTp9!fok");
        assertThat(validResponse.isValid()).isTrue();
        
        // Test with all lowercase (should be invalid - no uppercase)
        PasswordValidationResponse invalidResponse = passwordValidator.validate("abtp9!fok");
        assertThat(invalidResponse.isValid()).isFalse();
        assertThat(invalidResponse.getMessage()).isEqualTo("Senha deve conter pelo menos 1 letra maiúscula");
    }
} 