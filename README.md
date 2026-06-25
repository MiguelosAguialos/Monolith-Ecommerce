# Monolith E-commerce

API REST de um e-commerce simples construida com Spring Boot, JPA e PostgreSQL. O projeto centraliza o fluxo basico de uma loja online:

- cadastro e consulta de usuarios
- catalogo de produtos
- carrinho de compras por usuario
- criacao de pedidos a partir do carrinho

## Tecnologias

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Springdoc OpenAPI / Swagger UI
- Docker Compose

## Visao Geral

A aplicacao foi pensada como um monolito simples para fins de estudo e evolucao gradual. Ela expoe endpoints REST para:

- manter usuarios com endereco
- manter produtos com estoque, preco e status de ativacao
- manipular um carrinho por usuario
- finalizar um pedido com base no carrinho atual

## Documentacao da API

Depois de subir a aplicacao, acesse:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Requisitos

- Java 21
- Gradle
- PostgreSQL 16+ ou Docker
- Docker Compose opcional para subir a stack local completa

## Execucao Local

1. Garanta que o PostgreSQL esteja rodando localmente.
2. Ajuste o arquivo `configs/.env-dev` com suas credenciais e banco.
3. Execute a aplicacao:

```bash
./gradlew bootRun
```

No Windows:

```powershell
.\gradlew.bat bootRun
```

## Execucao com Docker

O projeto possui uma stack de desenvolvimento em `docker/dev`.

### 1. Subir com variaveis de ambiente

Execute o Compose a partir da pasta `docker/dev` informando o arquivo de ambiente:

```bash
docker compose --env-file ../../configs/.env-dev -f docker-compose.yml up -d
```

### 2. Verificar os servicos

- Aplicacao: `http://localhost:8080`
- Banco: `localhost:5432`

### 3. Parar a stack

```bash
docker compose --env-file ../../configs/.env-dev -f docker-compose.yml down
```

## Variaveis de Ambiente

O arquivo `configs/.env-dev` contem as configuracoes principais da aplicacao:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_DB`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

Importante:

- quando a aplicacao roda dentro do Docker, a URL do banco deve apontar para o servico `db`
- quando roda localmente fora do Docker, a URL pode apontar para `localhost`

## Endpoints Principais

### Users

- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`

### Products

- `GET /api/products`
- `GET /api/products/active`
- `POST /api/products`
- `GET /api/products/{id}`
- `GET /api/products/search?keyWord=...`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}`

### Cart

- `GET /api/cart`
- `POST /api/cart`
- `DELETE /api/cart/{productId}`

Header obrigatorio:

- `X-User-ID`

### Orders

- `POST /api/orders`

Header obrigatorio:

- `X-User-ID`

## Observacoes

- O projeto usa `springdoc-openapi` para gerar a documentacao automaticamente a partir das anotacoes nos controllers.
- O carrinho e os pedidos sao baseados no identificador do usuario enviado no header `X-User-ID`.
- Este repositorio foi organizado como um monolito para simplificar aprendizado, testes e evolucao futura.
