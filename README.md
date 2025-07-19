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
- **SpringDoc OpenAPI 2.6.0** (para documentação Swagger)

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

### URLs Disponíveis

| URL                                           | Descrição                                  | Status         |
| --------------------------------------------- | ------------------------------------------ | -------------- |
| `http://localhost:8080/`                      | Página inicial com links para documentação | ✅ Funcionando |
| `http://localhost:8080/swagger-ui.html`       | Interface interativa do Swagger UI         | ✅ Funcionando |
| `http://localhost:8080/v3/api-docs`           | Especificação OpenAPI em JSON              | ✅ Funcionando |
| `http://localhost:8080/api/password/validate` | Endpoint de validação de senhas            | ✅ Funcionando |

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

### Resolução de Problemas do OpenAPI

Durante o desenvolvimento, foram encontrados e resolvidos alguns problemas relacionados ao OpenAPI:

#### **Problema 1: Incompatibilidade de Versões**

**Sintoma:** Erro `NoSuchMethodError: 'void org.springframework.web.method.ControllerAdviceBean.<init>(java.lang.Object)'`

**Causa:** SpringDoc OpenAPI 2.5.0 não era compatível com Spring Boot 3.5.3

**Solução:** Atualização para SpringDoc OpenAPI 2.6.0

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

#### **Problema 2: Conflito no GlobalExceptionHandler**

**Sintoma:** OpenAPI retornando erro 500 com mensagem "Erro interno do servidor"

**Causa:** O `GlobalExceptionHandler` estava capturando exceções do OpenAPI/Swagger

**Solução:** Adicionada verificação para não interferir com exceções do SpringDoc

```java
@ExceptionHandler(Exception.class)
public ResponseEntity<PasswordValidationResponse> handleGenericException(Exception ex) {
    // Não captura exceções relacionadas ao OpenAPI/Swagger
    if (ex.getClass().getName().contains("springdoc") ||
        ex.getClass().getName().contains("swagger") ||
        ex.getClass().getName().contains("openapi") ||
        ex.getClass().getName().contains("ControllerAdviceBean")) {
        throw new RuntimeException(ex);
    }

    log.error("Erro interno da aplicação", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(PasswordValidationResponse.invalid("Erro interno do servidor"));
}
```

#### **Verificação de Funcionamento**

Para verificar se o OpenAPI está funcionando corretamente:

```bash
# Testar especificação OpenAPI
curl http://localhost:8080/v3/api-docs

# Testar Swagger UI
curl http://localhost:8080/swagger-ui/index.html

# Testar página inicial
curl http://localhost:8080/
```

**Resultado Esperado:**

- `/v3/api-docs` deve retornar JSON com a especificação OpenAPI completa
- `/swagger-ui.html` deve carregar a interface interativa do Swagger
- `/` deve retornar a página inicial com links para a documentação

#### **Configurações do OpenAPI**

O projeto inclui configurações específicas para o OpenAPI:

**Arquivo:** `src/main/java/com/desafio/itau/demo/config/OpenApiConfig.java`

```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Validador de Senhas - Desafio Itaú")
                .description("API REST para validação de senhas seguindo regras específicas de segurança...")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Desafio Itaú")
                    .url("https://www.itau.com")
                    .email("desafio@itau.com"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(Arrays.asList(
                new Server()
                    .url("http://localhost:8080")
                    .description("Servidor de Desenvolvimento")));
    }
}
```

**Anotações OpenAPI nos Controllers:**

```java
@Tag(name = "Validação de Senhas", description = "APIs para validação de senhas seguindo regras de segurança")
@RestController
public class PasswordValidationController {

    @Operation(
        summary = "Validar senha",
        description = "Valida se uma senha atende aos critérios de segurança estabelecidos..."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senha validada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping("/validate")
    public ResponseEntity<PasswordValidationResponse> validatePassword(
        @Valid @RequestBody PasswordValidationRequest request) {
        // implementação
    }
}
```

**Propriedades de Configuração:**

**Arquivo:** `src/main/resources/application.properties`

```properties
# Configurações do OpenAPI/Swagger
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method

# Configurações do Spring MVC
spring.mvc.static-path-pattern=/static/**
spring.web.resources.static-locations=classpath:/static/
spring.mvc.throw-exception-if-no-handler-found=false

# Configurações de logging
logging.level.com.desafio.itau=INFO
logging.level.org.springframework.web=WARN
```

**Dependências do OpenAPI:**

**Arquivo:** `pom.xml`

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

**Importante:** A versão 2.6.0 é necessária para compatibilidade com Spring Boot 3.5.3.

## Fluxo de Validação

```mermaid
flowchart TD
    A[Cliente envia requisição POST] --> B[PasswordValidationController]
    B --> C{Validação de entrada}
    C -->|Inválida| D[Retorna erro 400]
    C -->|Válida| E[PasswordValidationService]
    E --> F[PasswordValidator]

    F --> G{Senha é nula/vazia?}
    G -->|Sim| H[Retorna: Senha inválida]
    G -->|Não| I{Contém espaços?}

    I -->|Sim| J[Retorna: Senha inválida]
    I -->|Não| K{Menos de 9 caracteres?}

    K -->|Sim| L[Retorna: Senha inválida]
    K -->|Não| M{Contém dígito?}

    M -->|Não| N[Retorna: Senha inválida]
    M -->|Sim| O{Contém minúscula?}

    O -->|Não| P[Retorna: Senha inválida]
    O -->|Sim| Q{Contém maiúscula?}

    Q -->|Não| R[Retorna: Senha inválida]
    Q -->|Sim| S{Contém caractere especial?}

    S -->|Não| T[Retorna: Senha inválida]
    S -->|Sim| U{Contém caracteres repetidos?}

    U -->|Sim| V[Retorna: Senha inválida]
    U -->|Não| W[Retorna: Senha válida]

    H --> X[PasswordValidationResponse]
    J --> X
    L --> X
    N --> X
    P --> X
    R --> X
    T --> X
    V --> X
    W --> X

    X --> Y[Cliente recebe resposta JSON]
    D --> Y

    style A fill:#e1f5fe
    style Y fill:#e8f5e8
    style W fill:#c8e6c9
    style H fill:#ffcdd2
    style J fill:#ffcdd2
    style L fill:#ffcdd2
    style N fill:#ffcdd2
    style P fill:#ffcdd2
    style R fill:#ffcdd2
    style T fill:#ffcdd2
    style V fill:#ffcdd2
```

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

## Arquitetura da Aplicação

```mermaid
graph TB
    subgraph "Cliente"
        A[Cliente HTTP]
    end

    subgraph "Camada de Apresentação"
        B[PasswordValidationController]
        C[GlobalExceptionHandler]
    end

    subgraph "Camada de Serviço"
        D[PasswordValidationService]
        E[PasswordValidationServiceImpl]
    end

    subgraph "Camada de Validação"
        F[PasswordValidator]
        G[PasswordValidatorImpl]
    end

    subgraph "Camada de Modelo"
        H[PasswordValidationRequest]
        I[PasswordValidationResponse]
    end

    A -->|POST /api/password/validate| B
    B -->|Validação de entrada| C
    B -->|Delega validação| D
    D -->|Implementa| E
    E -->|Usa| F
    F -->|Implementa| G
    B -->|Recebe| H
    B -->|Retorna| I

    style A fill:#e3f2fd
    style B fill:#fff3e0
    style C fill:#fff3e0
    style D fill:#f3e5f5
    style E fill:#f3e5f5
    style F fill:#e8f5e8
    style G fill:#e8f5e8
    style H fill:#fce4ec
    style I fill:#fce4ec
```

## Decisões de Arquitetura

### 1. Separação de Responsabilidades

- **Controller**: Responsável apenas pela exposição da API REST
- **Service**: Contém a lógica de negócio de validação
- **Validator**: Implementa as regras específicas de validação

### 2. Princípios SOLID Aplicados

#### **S - Single Responsibility Principle (Princípio da Responsabilidade Única)**

- **`PasswordValidationController`**: Responsável apenas por receber requisições HTTP e retornar respostas
- **`PasswordValidationServiceImpl`**: Responsável apenas pela orquestração da validação
- **`PasswordValidatorImpl`**: Responsável apenas pela aplicação das regras de validação
- **`GlobalExceptionHandler`**: Responsável apenas pelo tratamento de exceções

#### **O - Open/Closed Principle (Princípio Aberto/Fechado)**

- **`PasswordValidator`** (interface): Permite extensão sem modificação do código existente
- Novas regras de validação podem ser implementadas criando novas classes que implementam a interface
- O sistema está aberto para extensão, mas fechado para modificação

#### **L - Liskov Substitution Principle (Princípio da Substituição de Liskov)**

- Qualquer implementação de `PasswordValidator` pode substituir `PasswordValidatorImpl` sem quebrar o comportamento
- Qualquer implementação de `PasswordValidationService` pode substituir `PasswordValidationServiceImpl`

#### **I - Interface Segregation Principle (Princípio da Segregação de Interface)**

- **`PasswordValidator`**: Interface específica para validação de senhas
- **`PasswordValidationService`**: Interface específica para serviços de validação
- Interfaces pequenas e focadas, evitando dependências desnecessárias

#### **D - Dependency Inversion Principle (Princípio da Inversão de Dependência)**

- **Controller** depende da interface `PasswordValidationService`, não da implementação
- **Service** depende da interface `PasswordValidator`, não da implementação
- Dependências injetadas via construtor, facilitando testes e manutenção

### 3. Qualidades de Arquitetura

#### **Abstração**

- **Interfaces bem definidas**: `PasswordValidator` e `PasswordValidationService` abstraem comportamentos
- **DTOs**: `PasswordValidationRequest` e `PasswordValidationResponse` abstraem dados de entrada/saída
- **Camadas de abstração**: Controller → Service → Validator, cada uma com responsabilidade específica

#### **Baixo Acoplamento**

- **Injeção de Dependência**: Classes dependem de interfaces, não de implementações concretas
- **Separação de Camadas**: Mudanças em uma camada não afetam outras
- **DTOs**: Isolam a API externa das estruturas internas
- **Interfaces**: Permitem troca de implementações sem afetar dependentes

#### **Alta Coesão**

- **`PasswordValidatorImpl`**: Todos os métodos trabalham com validação de senhas
- **`PasswordValidationServiceImpl`**: Todos os métodos trabalham com orquestração de validação
- **`PasswordValidationController`**: Todos os métodos trabalham com endpoints de validação
- **`GlobalExceptionHandler`**: Todos os métodos trabalham com tratamento de exceções

#### **Extensibilidade**

- **Novas regras de validação**: Implementar nova classe que implementa `PasswordValidator`
- **Novos tipos de validação**: Criar novas interfaces e implementações
- **Novos endpoints**: Adicionar métodos no controller sem afetar existentes
- **Novos formatos de resposta**: Estender DTOs sem quebrar compatibilidade

### 4. Clean Code

- **Nomes descritivos**: `validatePassword`, `hasRepeatedCharacters`, `containsSpecialCharacter`
- **Métodos pequenos**: Cada método tem uma responsabilidade específica
- **Código auto-documentado**: Nomes de métodos e variáveis explicam a intenção
- **Constantes nomeadas**: `SPECIAL_CHARACTERS`, `MIN_LENGTH`

### 5. Testabilidade

- **Injeção de Dependência**: Facilita mock de dependências nos testes
- **Interfaces**: Permitem criação de mocks para testes isolados
- **Métodos pequenos**: Facilitam testes unitários focados
- **Separação de responsabilidades**: Permitem testes de cada camada independentemente

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
- [x] Aplicar princípios SOLID e boas práticas de arquitetura
- [ ] Adicionar autenticação e autorização
- [ ] Implementar rate limiting
- [ ] Configurar monitoramento e métricas
- [ ] Deploy em ambiente de produção
