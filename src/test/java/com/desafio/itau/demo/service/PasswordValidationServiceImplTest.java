package com.desafio.itau.demo.service;

import com.desafio.itau.demo.model.PasswordValidationRequest;
import com.desafio.itau.demo.model.PasswordValidationResponse;
import com.desafio.itau.demo.validator.PasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o PasswordValidationServiceImpl
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Password Validation Service Tests")
class PasswordValidationServiceImplTest {
    
    @Mock
    private PasswordValidator passwordValidator;
    
    private PasswordValidationServiceImpl passwordValidationService;
    
    @BeforeEach
    void setUp() {
        passwordValidationService = new PasswordValidationServiceImpl(passwordValidator);
    }
    
    @Test
    @DisplayName("Should return valid response when validator returns valid")
    void shouldReturnValidResponseWhenValidatorReturnsValid() {
        // Given
        PasswordValidationRequest request = new PasswordValidationRequest("AbTp9!fok");
        PasswordValidationResponse expectedResponse = PasswordValidationResponse.valid("Senha válida");
        
        when(passwordValidator.validate("AbTp9!fok")).thenReturn(expectedResponse);
        
        // When
        PasswordValidationResponse actualResponse = passwordValidationService.validatePassword(request);
        
        // Then
        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(passwordValidator).validate("AbTp9!fok");
    }
    
    @Test
    @DisplayName("Should return invalid response when validator returns invalid")
    void shouldReturnInvalidResponseWhenValidatorReturnsInvalid() {
        // Given
        PasswordValidationRequest request = new PasswordValidationRequest("aa");
        PasswordValidationResponse expectedResponse = PasswordValidationResponse.invalid("Senha deve ter pelo menos 9 caracteres");
        
        when(passwordValidator.validate("aa")).thenReturn(expectedResponse);
        
        // When
        PasswordValidationResponse actualResponse = passwordValidationService.validatePassword(request);
        
        // Then
        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(passwordValidator).validate("aa");
    }
    
    @Test
    @DisplayName("Should handle exception and return error response")
    void shouldHandleExceptionAndReturnErrorResponse() {
        // Given
        PasswordValidationRequest request = new PasswordValidationRequest("AbTp9!fok");
        
        when(passwordValidator.validate("AbTp9!fok")).thenThrow(new RuntimeException("Test exception"));
        
        // When
        PasswordValidationResponse actualResponse = passwordValidationService.validatePassword(request);
        
        // Then
        assertThat(actualResponse.isValid()).isFalse();
        assertThat(actualResponse.getMessage()).isEqualTo("Erro interno durante a validação");
        verify(passwordValidator).validate("AbTp9!fok");
    }
    
    @Test
    @DisplayName("Should delegate validation to validator")
    void shouldDelegateValidationToValidator() {
        // Given
        PasswordValidationRequest request = new PasswordValidationRequest("testPassword");
        PasswordValidationResponse expectedResponse = PasswordValidationResponse.valid("Senha válida");
        
        when(passwordValidator.validate("testPassword")).thenReturn(expectedResponse);
        
        // When
        passwordValidationService.validatePassword(request);
        
        // Then
        verify(passwordValidator, times(1)).validate("testPassword");
    }
} 