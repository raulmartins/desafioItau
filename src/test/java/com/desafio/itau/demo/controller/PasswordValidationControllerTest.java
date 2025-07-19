package com.desafio.itau.demo.controller;

import com.desafio.itau.demo.model.PasswordValidationRequest;
import com.desafio.itau.demo.model.PasswordValidationResponse;
import com.desafio.itau.demo.service.PasswordValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para o PasswordValidationController
 */
@WebMvcTest(PasswordValidationController.class)
@DisplayName("Password Validation Controller Integration Tests")
class PasswordValidationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PasswordValidationService passwordValidationService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @DisplayName("Should return 200 OK for valid password")
    void shouldReturn200OkForValidPassword() throws Exception {
        // Given
        PasswordValidationRequest request = new PasswordValidationRequest("AbTp9!fok");
        PasswordValidationResponse response = PasswordValidationResponse.valid("Senha válida");
        
        when(passwordValidationService.validatePassword(any(PasswordValidationRequest.class)))
            .thenReturn(response);
        
        // When & Then
        mockMvc.perform(post("/api/password/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.valid").value(true))
            .andExpect(jsonPath("$.message").value("Senha válida"));
    }
    
    @Test
    @DisplayName("Should return 200 OK for invalid password")
    void shouldReturn200OkForInvalidPassword() throws Exception {
        // Given
        PasswordValidationRequest request = new PasswordValidationRequest("aa");
        PasswordValidationResponse response = PasswordValidationResponse.invalid("Senha deve ter pelo menos 9 caracteres");
        
        when(passwordValidationService.validatePassword(any(PasswordValidationRequest.class)))
            .thenReturn(response);
        
        // When & Then
        mockMvc.perform(post("/api/password/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.valid").value(false))
            .andExpect(jsonPath("$.message").value("Senha deve ter pelo menos 9 caracteres"));
    }
    
    @Test
    @DisplayName("Should return 400 Bad Request for empty password")
    void shouldReturn400BadRequestForEmptyPassword() throws Exception {
        // Given
        PasswordValidationRequest request = new PasswordValidationRequest("");
        
        // When & Then
        mockMvc.perform(post("/api/password/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.valid").value(false))
            .andExpect(jsonPath("$.message").value("A senha não pode estar vazia"));
    }
    
    @Test
    @DisplayName("Should return 400 Bad Request for null password")
    void shouldReturn400BadRequestForNullPassword() throws Exception {
        // Given
        PasswordValidationRequest request = new PasswordValidationRequest(null);
        
        // When & Then
        mockMvc.perform(post("/api/password/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.valid").value(false))
            .andExpect(jsonPath("$.message").value("A senha não pode estar vazia"));
    }
    
    @Test
    @DisplayName("Should return 400 Bad Request for missing password field")
    void shouldReturn400BadRequestForMissingPasswordField() throws Exception {
        // Given
        String requestJson = "{}";
        
        // When & Then
        mockMvc.perform(post("/api/password/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.valid").value(false))
            .andExpect(jsonPath("$.message").value("A senha não pode estar vazia"));
    }
    
    @Test
    @DisplayName("Should return 400 Bad Request for invalid JSON")
    void shouldReturn400BadRequestForInvalidJson() throws Exception {
        // Given
        String invalidJson = "{ invalid json }";
        
        // When & Then
        mockMvc.perform(post("/api/password/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Should return 415 Unsupported Media Type for non-JSON content")
    void shouldReturn415UnsupportedMediaTypeForNonJsonContent() throws Exception {
        // Given
        String requestContent = "password=AbTp9!fok";
        
        // When & Then
        mockMvc.perform(post("/api/password/validate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(requestContent))
            .andExpect(status().isUnsupportedMediaType());
    }
} 