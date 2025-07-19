# Validador de Senhas - Desafio Itaú

## Descrição

API REST para validação de senhas seguindo regras específicas de segurança.

## Regras de Validação

Uma senha é considerada válida quando possui:

- **Nove ou mais caracteres**
- **Ao menos 1 dígito**
- **Ao menos 1 letra minúscula**
- **Ao menos 1 letra maiúscula**
- **Ao menos 1 caractere especial** (!@#$%^&\*()-+)
- **Não possuir caracteres repetidos**
- **Não possuir espaços em branco**

## Tecnologias Utilizadas

- **Java 24**
- **Spring Boot 3.5.3**
- **Maven**
- **JUnit 5** (para testes)
- **Lombok** (para redução de boilerplate)
- **SpringDoc OpenAPI** (para documentação Swagger)

## Como Executar

### Pré-requisitos

- Java 24 ou superior
- Maven 3.6+

### Executando a aplicação

```bash
# Clone o repositório
git clone https://github.com/raulmartins/desafioItau.git
cd desafioItau

# Execute a aplicação
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## Documentação da API

### Swagger UI

A documentação interativa da API está disponível através do Swagger UI:

**URL:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

O Swagger UI permite:

- Visualizar todos os endpoints disponíveis
- Testar as APIs diretamente no navegador
- Ver exemplos de request/response
- Explorar os modelos de dados

### OpenAPI Specification

A especificação OpenAPI em formato JSON está disponível em:

**URL:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Esta especificação pode ser importada em ferramentas como:

- Postman
- Insomnia
- Outras ferramentas de teste de API

## API Endpoints

### POST /api/password/validate

Valida se uma senha atende aos critérios de segurança.

**Request:**

```json
{
  "password": "AbTp9!fok"
}
```

**Response:**

```json
{
  "isValid": true,
  "message": "Senha válida"
}
```

## Exemplos de Validação

| Senha         | Válida | Motivo                                                      |
| ------------- | ------ | ----------------------------------------------------------- |
| `""`          | ❌     | Senha vazia                                                 |
| `"aa"`        | ❌     | Menos de 9 caracteres, sem maiúsculas, dígitos ou especiais |
| `"ab"`        | ❌     | Menos de 9 caracteres, sem maiúsculas, dígitos ou especiais |
| `"AAAbbbCc"`  | ❌     | Sem dígitos ou caracteres especiais                         |
| `"AbTp9!foo"` | ❌     | Caracteres repetidos                                        |
| `"AbTp9!foA"` | ❌     | Caracteres repetidos                                        |
| `"AbTp9 fok"` | ❌     | Contém espaço em branco                                     |
| `"AbTp9!fok"` | ✅     | Atende todos os critérios                                   |

## Executando os Testes

```bash
./mvnw test
```

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/desafio/itau/demo/
│   │   ├── controller/     # Controllers REST
│   │   ├── service/        # Lógica de negócio
│   │   ├── model/          # DTOs e entidades
│   │   └── validator/      # Validadores customizados
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/desafio/itau/demo/
        ├── controller/     # Testes de integração
        ├── service/        # Testes unitários
        └── validator/      # Testes de validação
```

## Decisões de Arquitetura

### 1. Separação de Responsabilidades

- **Controller**: Responsável apenas pela exposição da API REST
- **Service**: Contém a lógica de negócio de validação
- **Validator**: Implementa as regras específicas de validação

### 2. Princípios SOLID

- **Single Responsibility**: Cada classe tem uma responsabilidade específica
- **Open/Closed**: Fácil extensão para novas regras de validação
- **Dependency Inversion**: Uso de interfaces para baixo acoplamento

### 3. Clean Code

- Nomes descritivos para métodos e variáveis
- Métodos pequenos e focados
- Código auto-documentado

### 4. Testabilidade

- Testes unitários para cada regra de validação
- Testes de integração para a API
- Cobertura de casos edge e cenários de erro

## Premissas Assumidas

1. **Caracteres especiais**: Considerados apenas os especificados (!@#$%^&\*()-+)
2. **Espaços em branco**: Qualquer tipo de espaço (tab, newline, etc.) é inválido
3. **Case sensitivity**: A validação é case-sensitive
4. **API REST**: Retorna JSON com status HTTP apropriado
5. **Validação**: Senha nula ou vazia é considerada inválida

## Próximos Passos

- [x] Implementar validador de senhas
- [x] Criar controller REST
- [x] Implementar testes unitários
- [x] Implementar testes de integração
- [x] Adicionar documentação Swagger
- [x] Configurar logging
- [x] Implementar tratamento de erros
- [ ] Adicionar autenticação e autorização
- [ ] Implementar rate limiting
- [ ] Configurar monitoramento e métricas
- [ ] Deploy em ambiente de produção
