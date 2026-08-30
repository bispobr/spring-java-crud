# API REST para Gerenciamento de Produtos

API REST desenvolvida com Java e Spring Boot para gerenciamento de produtos, disponibilizando operações de criação, consulta, atualização e exclusão.

O projeto também demonstra práticas comuns no desenvolvimento de APIs REST, incluindo validação, tratamento global de exceções, cache, documentação OpenAPI, monitoramento, persistência com PostgreSQL, versionamento do banco de dados e execução em containers.

## Funcionalidades

- Cadastro de produtos
- Listagem de produtos
- Consulta de produto por ID
- Atualização de produto
- Exclusão de produto
- Validação dos dados de entrada
- Tratamento global de exceções com `@RestControllerAdvice`
- Cache local
- Logging
- Documentação interativa com Swagger/OpenAPI
- Monitoramento com Spring Boot Actuator
- Persistência com PostgreSQL
- Versionamento de banco de dados com Flyway
- Execução com Docker Compose

## Tecnologias

- Java 21+
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Flyway
- Spring Boot Actuator
- Swagger/OpenAPI
- Lombok
- Maven
- Docker
- Docker Compose

## Requisitos

- Java 21+
- Maven
- Docker e Docker Compose
- PostgreSQL, caso a aplicação seja executada sem Docker

## Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/bispobr/spring-java-crud.git
cd spring-java-crud
```

Configure o `application.properties` de acordo com o ambiente utilizado e informe as configurações necessárias para conexão com o PostgreSQL.

Execute a aplicação com Maven:

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Swagger / OpenAPI

Com a aplicação em execução, acesse a documentação interativa:

```text
http://localhost:8080/swagger-ui/index.html
```

## Actuator

Endpoint de saúde da aplicação:

```text
http://localhost:8080/actuator/health
```

## API Endpoints

### Criar produto

```http
POST /produto
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "Notebook",
  "preco": 3500
}
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `nome` | `String` | Nome do produto. |
| `preco` | `Integer` | Preço do produto. |

### Listar produtos

```http
GET /produto
```

Retorna todos os produtos cadastrados.

### Buscar produto por ID

```http
GET /produto/{id}
```

Retorna o produto correspondente ao ID informado.

### Atualizar produto

```http
PUT /produto/{id}
Content-Type: application/json
```

Exemplo:

```json
{
  "nome": "Notebook atualizado",
  "preco": 3800
}
```

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `id` | `String` | Identificador do produto. |
| `nome` | `String` | Nome do produto. |
| `preco` | `Integer` | Preço do produto. |

### Excluir produto

```http
DELETE /produto/{id}
```

Remove o produto correspondente ao ID informado.

## Banco de dados

A aplicação utiliza PostgreSQL como banco de dados relacional.

As alterações do schema são gerenciadas pelo Flyway por meio de migrations versionadas.

## Cache

O projeto utiliza cache local para reduzir consultas repetidas, conforme a implementação da aplicação.

## Docker

Para executar a aplicação utilizando Docker Compose:

```bash
mvn clean package
docker-compose up --build
```

Os serviços necessários serão iniciados conforme a configuração do `docker-compose.yml`.

## Testes

Execute os testes automatizados com:

```bash
mvn test
```

## Fluxo simplificado

```text
Cliente
   │
   ▼
API REST
   │
   ▼
Validação
   │
   ▼
Serviço
   │
   ├── Cache
   │
   ▼
Persistência JPA
   │
   ▼
PostgreSQL
```

## Status

Projeto desenvolvido para praticar a construção de APIs REST com Spring Boot, incluindo CRUD, persistência com PostgreSQL, Flyway, cache, validação, tratamento de exceções, documentação OpenAPI, monitoramento e execução em containers.
