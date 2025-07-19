package com.desafio.itau.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração do OpenAPI/Swagger para documentação da API.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configuração do OpenAPI com informações da API.
     * 
     * @return Configuração do OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Validador de Senhas - Desafio Itaú")
                        .description("API REST para validação de senhas seguindo regras específicas de segurança. " +
                                "A API verifica se uma senha atende aos critérios estabelecidos incluindo " +
                                "comprimento mínimo, presença de diferentes tipos de caracteres e ausência de repetições.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Desafio Itaú")
                                .email("desafio@itau.com")
                                .url("https://www.itau.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento")
                ));
    }
} 