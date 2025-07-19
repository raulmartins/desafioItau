package com.desafio.itau.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller para a página inicial da aplicação.
 */
@RestController
public class HomeController {

    /**
     * Endpoint raiz que retorna informações sobre a API.
     * 
     * @return Informações sobre a API de validação de senhas
     */
    @GetMapping("/")
    public String home() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Validador de Senhas - Desafio Itaú</title>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; }
                    .container { max-width: 800px; margin: 0 auto; }
                    .header { background: #f8f9fa; padding: 20px; border-radius: 5px; }
                    .endpoint { background: #e9ecef; padding: 15px; margin: 10px 0; border-radius: 3px; }
                    .link { color: #007bff; text-decoration: none; }
                    .link:hover { text-decoration: underline; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🔐 Validador de Senhas - Desafio Itaú</h1>
                        <p>API REST para validação de senhas seguindo regras específicas de segurança.</p>
                    </div>
                    
                    <h2>📚 Documentação</h2>
                    <div class="endpoint">
                        <strong>Swagger UI:</strong> 
                        <a href="/swagger-ui.html" class="link">/swagger-ui.html</a>
                        <br>
                        <small>Interface interativa para testar a API</small>
                    </div>
                    
                    <div class="endpoint">
                        <strong>OpenAPI JSON:</strong> 
                        <a href="/v3/api-docs" class="link">/v3/api-docs</a>
                        <br>
                        <small>Especificação OpenAPI em formato JSON</small>
                    </div>
                    
                    <h2>🚀 Endpoints Disponíveis</h2>
                    <div class="endpoint">
                        <strong>POST /api/password/validate</strong>
                        <br>
                        <small>Valida se uma senha atende aos critérios de segurança</small>
                    </div>
                    
                    <h2>📋 Regras de Validação</h2>
                    <ul>
                        <li>Nove ou mais caracteres</li>
                        <li>Ao menos 1 dígito</li>
                        <li>Ao menos 1 letra minúscula</li>
                        <li>Ao menos 1 letra maiúscula</li>
                        <li>Ao menos 1 caractere especial (!@#$%^&*()-+)</li>
                        <li>Não possuir caracteres repetidos</li>
                        <li>Não possuir espaços em branco</li>
                    </ul>
                    
                    <h2>🧪 Exemplo de Uso</h2>
                    <div class="endpoint">
                        <strong>Request:</strong>
                        <pre>POST /api/password/validate
Content-Type: application/json

{
  "password": "AbTp9!fok"
}</pre>
                        
                        <strong>Response:</strong>
                        <pre>{
  "valid": true,
  "message": "Senha válida"
}</pre>
                    </div>
                </div>
            </body>
            </html>
            """;
    }
} 