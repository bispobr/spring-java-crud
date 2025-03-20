# Spring java crud
Este repositório contém um projeto CRUD simples construído usando Java Spring. O objetivo deste repositório é praticar e construir todos os métodos CRUD usando o Java Spring.

## Instalação

1. Clone o repositório:

```bash
git https://github.com/bispobr/spring-java-crud.git
```

2. Instale as dependências com Maven

## Como usar

1. Inicie a aplicação 
2. A API está acessivel atraves do endereço http://localhost:8080


## API Endpoints
A API contem os seguintes endpoints :

```http request
GET /produto - Retorna uma Lista com todos os objetos.
```

```http request
POST /produto - Registra um novo objeto.
Content-Type: application/json

{
  "nome": "xxxxxx",
  "preco": 00000
}
```

```http request
PUT / - Altera Um objeto.
Content-Type: application/json

{
 "id": "xxxxxxx",
 "nome": "xxxxxx",
 "preco": 00
}
```

```http request
DELETE / - Exclui Um objeto.
Content-Type: application/json

{
 "id": "xxxxxxx",
 "nome": "xxxxxx",
 "preco": 00
}
```

## Banco de Dados
Esse projeto utiliza o PostgresSQL como Banco de Dados. Todas as migrations são gerenciadas atraves do Flyway.

