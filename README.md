# API REST para Gerenciamento de Produtos

## Descrição

Esta aplicação é uma API REST desenvolvida para gerenciar produtos, oferecendo suporte  às operações básicas de um CRUD:
- **Criar** um produto
- **Listar** produtos
- **Atualizar** um produto existente
- **Excluir** um produto

## Tecnologias Utilizadas

- **Java + Spring Boot** – Framework principal da aplicação
- **Lombok (@Slf4j)** – Geração de logs
- **Cache** – uso de cache local
- **Tratamento de Exceções** - @RestControllerAdvice
- **Swagger** – Documentação interativa da API
- **Spring Boot Actuator** – Monitoramento e verificação de saúde da aplicação
- **Integração Actuator + Swagger** – Permite monitorar a saúde da API diretamente pela interface de documentação
- **PostgreSQL** – Banco de dados relacional utilizado
- **Docker** – criação, implantação e gerenciamento de aplicações dentro de contêineres.
- **Flyway** – Gerenciamento e versionamento das migrations do banco de dados

## Requisitos

- Java 21+
- Maven
- PostgreSQL

## Executando o Projeto

1. Clone o repositório:

```bash
git https://github.com/bispobr/spring-java-crud.git
```

2. Altere o arquivo de configuração **application.properties** com as credenciais de login e acesso do PostgreSQL do seu ambiente.

## Como usar

1. Inicie a aplicação
2. A API está acessível através do endereço http://localhost:8080
3. A documentação da API está acessível através do Link http://localhost:8080/swagger-ui/index.html#/
4. O endpoint de saúde e métricas do Actuator está acessível através do Link http://localhost:8080/actuator/health

## Como Rodar em um Container (Opcional)

1. Construa o projeto:

```bash
mvn clean package 
```

2. Gere a Imagem Docker. Com o Docker  instalado execute:

```bash
docker-compose up --build
```

## API Endpoints
API contem os seguintes endpoints:

```http request
POST /produto - Cadastra um novo produto
Content-Type: application/json

{
  "nome": "xxxxxx",
  "preco": 00000
}
```
| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `nome` | `String` | **Obrigatório**. O nome do produto 
| `preco` | `Integer` | **Obrigatório**. O preço do produto 


```http request
GET /produto -  Lista todos os produtos
```

```http request
GET /produto/{id} -  Lista produto por id
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `String` | **Obrigatório**. O id do produto 

```http request
PUT /produto/{id} - Atualizar um produto existente
Content-Type: application/json

{
 "nome": "xxxxxx",
 "preco": 00
}
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `String` | **Obrigatório**. O id do produto 
| `nome` | `String` | **Obrigatório**. O nome do produto 
| `preco` | `Integer` | **Obrigatório**. O preço do produto 

```http request
DELETE /produto/{id} - Remover  produto de id especificado.
```

