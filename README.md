# API Catálogo-Produtos
API para catálogo de produtos.

## 📜 Sumário
1. [Detalhes do projeto](https://github.com/danjusto/api-catalogo-produtos#1--detalhes-do-projeto)
2. [Tecnologias usadas](https://github.com/danjusto/api-catalogo-produtos#2--tecnologias-usadas)
3. [Para rodar o projeto](https://github.com/danjusto/api-catalogo-produtos#3--para-rodar-o-projeto)
4. [Documentação](https://github.com/danjusto/api-catalogo-produtos#4--documenta%C3%A7%C3%A3o)
5. [Autor](https://github.com/danjusto/api-catalogo-produtos#5--autor)

## 1. 🔍 Detalhes do projeto
A API Catálogo-Produtos tem como objetivo persistir dados para gerenciamento de um catálogo de produtos. Foi realizado para um teste de um estágio.

#### Cenário:
* Sistema permite o cadastro de usuário, com validação de informações;
* Sistema recupera e armazena, caso a tabela de produtos esteja vazia, os produtos da API externa: https://dummyjson.com/products/category/smartphones e https://dummyjson.com/products/category/laptops
* Sistema permite apenas as requisições de cadastro de usuário e login sem autenticação por meio de JWT;
* Com usuário logado, o sistema permite a criação, listagem, detalhamento, edição e deleção de produtos;
* Sistema armazena em cache (Redis) o resultado das buscas de listagem e detalhamento de produtos;

## 2. 💻 Tecnologias usadas
<div align="center">

Languages, Frameworks & Librarys:   
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![SpringBoot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![JSON](https://img.shields.io/badge/json-5E5C5C?style=for-the-badge&logo=json&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

Tests:  
![Insomnia](https://img.shields.io/badge/Insomnia-5849be?style=for-the-badge&logo=Insomnia&logoColor=white)
![JUnit](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

Cache:  
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white)

Database:  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

IDE:  
![Intellij](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

</div>

## 3. 🔌 Para rodar o projeto
1. Instale as dependências necessárias com o Maven para rodar a API (relacionadas no pom.xml):

    ```
    mvn dependency:copy-dependencies
    ```
2. A API utiliza o PostgreSQL como banco de dados e o Redis como cache, então se faz necessário que você verifique se o arquivo `docker-compose.yaml` não possui confronto de portas com as existentes no sistema local. Para criar um container com os servidores basta rodar o comando abaixo:

    ```
    docker compose up -d
    ```
3. Se alguma configuração no arquivo `docker-compose.yaml` foi alterada, verifique as propriedades de conexão com o banco de dados e o cache no arquivo `application.properties`:
    ```
    spring.datasource.url=jdbc:jdbc:postgresql://localhost:{porta}/{nome_db}
    spring.datasource.username={username}
    spring.datasource.password={password}
   
   redis.host=localhost
   redis.port={porta}
    ```

4. Rode a aplicação que o sistema já irá criar as tabelas e popular a tabela produtos automaticamente, deixando-as prontas para uso. Por padrão, a aplicação rodará na porta 8080.

5. Você precisará de uma ferramenta de teste de requisições como o [Insomnia](https://insomnia.rest/) ou utilizar o [Swagger-Ui](http://localhost:8080/swagger-ui/index.html), devendo seguir as orientações da documentação abaixo para utilizar a API.

6. Você também pode usar o front-end desenvolvido para teste desta api: [Front-end](https://github.com/danjusto/front-catalogo-produto)

7. Você pode rodar os testes automatizados criados com JUnit, caso queira (mais testes em desenvolvimento).

## 4. 🔌 Documentação
### Endpoints

**Login** - Autenticação de usuário <br/>

<details>
<summary><b>POST users/login</b></summary>

Logar com um usuário por meio de `username` e `password`. Retorna um token JWT para ser utilizado nas requisições.

**Request**

| **Nome** | **Obrigatório** | **Tipo** | **Descrição**     |
|:---------| :-------------- | :------- | :---------------- |
| username | sim             | `string` | E-mail do usuário |
| password | sim             | `string` | Senha do usuário  |

> **_NOTA:_** Não é necessário enviar Token JWT via Authorization Header.

Exemplo de requisição:

```json
{
    "username": "fulaninho",
    "password": "password"
}
```

**Response**

Sucesso

```json
{
    "type": "Bearer",
    "token": "abcdefghijklmno.abcdefghijklmnopqrstuvwxyz.abcdefghijklmnop"
}
```

`status: 201` <br /><br />

Erro comum

```json
{
   "statusCode": "UNAUTHORIZED",
   "message": "Bad credentials",
   "dateTime": "02-12-2023 19:00:24"
}
```

</details>
<br/>

**User** - Criação de um novo usuário, edição de um usuário, detalhamento de um usuário e deleção do usuário <br/>

<details>
<summary><b>POST user</b></summary>

Criar um usuário para poder utilizar a API.

**Request**

| **Nome** | **Obrigatório** | **Tipo** | **Descrição**       |
|:---------| :-------------- | :------- |:--------------------|
| name     | sim             | `string` | Nome do usuário     |
| username | sim             | `string` | Username do usuário |
| email    | sim             | `string` | Email do usuário    |
| password | sim             | `string` | Senha do usuário    |

> **_NOTA:_** Não é necessário enviar Token JWT via Authorization Header.

Exemplo de requisição:

```json
{
    "name": "Fulano de Ciclano",
    "username": "fulaninho",
    "email": "fulano@email.com",
    "password": "password"
}
```

**Response**

Sucesso

```json
{
    "id": "753ae2c4-edd0-4d4c-8f7f-1cffa5eea4f6",
    "name": "Fulano de Ciclano",
    "username": "fulaninho",
    "email": "fulano@email.com"
}
```

`status: 201` <br /><br /> Erro comum

```json
{
   "statusCode": "BAD_REQUEST",
   "message": "Email already in use",
   "dateTime": "29-11-2023 21:31:14"
}
```

</details>

<details>
<summary><b>PUT user</b></summary>

Editar um usuário. Apenas nome e email podem ser alterados, mas nenhum dos dois é obrigatório.

**Request**

| **Nome** | **Obrigatório** | **Tipo** | **Descrição**    |
|:---------| :-------------- | :------- | :--------------- |
| name     | não             | `string` | Nome do usuário  |
| email    | não             | `string` | Email do usuário |

> **_NOTA:_** É necessário enviar Token JWT via Authorization Header.

Exemplo de requisição:

```json
{
   "name": "Fulano Editado",
   "email": "fulano.editado@email.com",
   "password": "password"
}
```

**Response**

Sucesso

```json
{
   "id": "753ae2c4-edd0-4d4c-8f7f-1cffa5eea4f6",
   "name": "Fulano de Ciclano Editado",
   "username": "fulaninho",
   "email": "fulano.editado@email.com"
}
```

`status: 200` <br/><br/> Erros comuns

```json
{
   "statusCode": "BAD REQUEST",
   "message": "User not found",
   "dateTime": "02-12-2023 19:21:55"
}
```

```json
{
   "statusCode": "UNAUTHORIZED",
   "message": "Invalid token",
   "dateTime": "02-12-2023 19:21:55"
}
```

```json
{
   "statusCode": "BAD_REQUEST",
   "message": "Email already in use",
   "dateTime": "29-11-2023 21:31:14"
}
```

</details>

<details>
<summary><b>GET user</b></summary>

Detalhar um usuário. Não é necessário enviar qualquer dado na requisição.


> **_NOTA:_** É necessário enviar Token JWT via Authorization Header.

**Response**

Sucesso

```json
{
   "id": "753ae2c4-edd0-4d4c-8f7f-1cffa5eea4f6",
   "name": "Fulano de Ciclano",
   "username": "fulaninho",
   "email": "fulano@email.com"
}
```

`status: 200` <br/><br/> Erros comuns

```json
{
   "statusCode": "BAD REQUEST",
   "message": "User not found",
   "dateTime": "02-12-2023 19:21:55"
}
```

```json
{
   "statusCode": "UNAUTHORIZED",
   "message": "Invalid token",
   "dateTime": "02-12-2023 19:21:55"
}
```

</details>

<details>
<summary><b>DELETE user</b></summary>

Deletar o usuario. Não é necessário enviar qualquer dado na requisição.

> **_NOTA:_** É necessário enviar Token JWT via Authorization Header.

**Response**

Sucesso  
`no body returned for response` <br/> `status: 204` <br/>

Erro comum

```json
{
   "statusCode": "BAD REQUEST",
   "message": "User not found",
   "dateTime": "02-12-2023 19:21:55"
}
```

```json
{
   "statusCode": "UNAUTHORIZED",
   "message": "Invalid token",
   "dateTime": "02-12-2023 19:21:55"
}
```

</details>
<br/>

**Products** - Criação de um novo produto, edição de um produto, listagem de produtos, deleção de um produto e detalhamento de um produto. <br/>

<details>
<summary><b>POST products</b></summary>

Criar um filme.

**Request**

| **Nome**    | **Obrigatório** | **Tipo**     | **Descrição**            |
|:------------|:----------------|:-------------|:-------------------------|
| title       | sim             | `string`     | Nome do produto          |
| description | sim             | `string`     | Descrição do produto     |
| price       | sim             | `bigdecimal` | Preço do produto         |
| stock       | sim             | `integer`    | Estoque do produto       |
| brand       | sim             | `string`     | Marca do produto         |
| category    | sim             | `string`     | Categoria do produto     |
| image       | não             | `string`     | URL da imagem do produto |

> **_NOTA:_** É necessário enviar Token JWT via Authorization Header.

Exemplo de requisição:

```json
{
   "title": "Iphone 12",
   "description": "Qualquer coisa",
   "price": 2499.99,
   "stock": 25,
   "brand": "Apple",
   "category": "smartphones"
}
```

**Response**

Sucesso

```json
{
   "id": "590ced29-016a-4c7e-bf35-818e2bed8539",
   "title": "Iphone 12",
   "description": "Qualquer coisa",
   "price": 2499.99,
   "stock": 25,
   "brand": "Apple",
   "category": "SMARTPHONES",
   "image": null
}
```

`status: 201` <br /><br /> Erros comuns

```json
{
   "statusCode": "BAD_REQUEST",
   "message": "Product already exist",
   "dateTime": "02-12-2023 19:39:38"
}
```
```json
{
   "statusCode": "BAD_REQUEST",
   "message": "Invalid category. Categories available: 'smartphones', 'laptops'",
   "dateTime": "02-12-2023 19:59:50"
}
```
```json
{
   "statusCode": "UNAUTHORIZED",
   "message": "Invalid token",
   "dateTime": "02-12-2023 19:37:36"
}
```

</details>

<details>
<summary><b>GET products</b></summary>

Listar produtos. É possível passar um parâmetro `query` para buscar por categoria.

**Request**

| **Nome** | **Obrigatório** | **Tipo**     | **Descrição**            |
|:---------|:----------------|:-------------|:-------------------------|
| category | não             | `string`     | Enviado via query na url |

> **_NOTA:_** É necessário enviar Token JWT via Authorization Header.

Exemplo de requisição:

`http://localhost:8080/products?category=laptop`

**Response**

Sucesso

```json
[
   {
      "id": "201c2e19-a6e1-4036-8678-692b50d5b246",
      "title": "MacBook Pro",
      "description": "MacBook Pro 2021 with mini-LED display may launch between September, November",
      "price": 6996.00,
      "stock": 83,
      "brand": "Apple",
      "category": "LAPTOPS",
      "image": "https://i.dummyjson.com/data/products/6/1.png"
   },
   {
      "id": "e9812960-678b-4fad-9756-9b936aaed303",
      "title": "Samsung Galaxy Book",
      "description": "Samsung Galaxy Book S (2020) Laptop With Intel Lakefield Chip, 8GB of RAM Launched",
      "price": 5996.00,
      "stock": 50,
      "brand": "Samsung",
      "category": "LAPTOPS",
      "image": "https://i.dummyjson.com/data/products/7/1.jpg"
   },
   {
      "id": "b2d77720-4267-4d13-9f2a-2cd9c2d6902f",
      "title": "Microsoft Surface Laptop 4",
      "description": "Style and speed. Stand out on HD video calls backed by Studio Mics. Capture ideas on the vibrant touchscreen.",
      "price": 5996.00,
      "stock": 68,
      "brand": "Microsoft Surface",
      "category": "LAPTOPS",
      "image": "https://i.dummyjson.com/data/products/8/1.jpg"
   }
]
```

`status: 200`

Sucesso sem retorno

```json
[]
```
`status: 200` <br/>

</details>

<details>
<summary><b>GET products/id</b></summary>

Detalhar um produto. O `id` deve ser enviado na url.

**Request**

| **Nome** | **Obrigatório** | **Tipo** | **Descrição**                    |
| :------- | :-------------- | :------- | :------------------------------- |
| id       | sim             | `string` | **Enviar via parâmetro de rota** |

> **_NOTA:_** É necessário enviar Token JWT via Authorization Header.

**Response**

Sucesso

```json
{
   "id": "4b5a9bcd-a10e-4965-8939-22d24f408138",
   "title": "Infinix INBOOK",
   "description": "Infinix Inbook X1 Ci3 10th 8GB 256GB 14 Win10 Grey – 1 Year Warranty",
   "price": 4396.00,
   "stock": 96,
   "brand": "Infinix",
   "category": "LAPTOPS",
   "image": "https://i.dummyjson.com/data/products/9/1.jpg"
}
```

`status: 200`

Erros comuns

```json
{
   "statusCode": "NOT_FOUND",
   "message": "Product not found",
   "dateTime": "02-12-2023 19:48:00"
}
```
```json
{
   "statusCode": "UNAUTHORIZED",
   "message": "Invalid token",
   "dateTime": "02-12-2023 19:48:12"
}
```

</details>

<details>
<summary><b>PUT products/id</b></summary>

Alterar os dados do produto. O `id` deve ser enviado na url. Nenhuma propriedade do body é obrigatória, mas as validações persistem. 

**Request**

| **Nome**    | **Obrigatório** | **Tipo**     | **Descrição**            |
|:------------|:----------------|:-------------|:-------------------------|
| id       | sim             | `string` | **Enviar via parâmetro de rota** |
| title       | não             | `string`     | Nome do produto          |
| description | não             | `string`     | Descrição do produto     |
| price       | não             | `bigdecimal` | Preço do produto         |
| stock       | não             | `integer`    | Estoque do produto       |
| brand       | não             | `string`     | Marca do produto         |
| category    | não             | `string`     | Categoria do produto     |
| image       | não             | `string`     | URL da imagem do produto |

> **_NOTA:_** É necessário enviar Token JWT via Authorization Header.

**Response**

Sucesso

```json
{
   "id": "4b5a9bcd-a10e-4965-8939-22d24f408138",
   "title": "Infinix INBOOK EDITADO",
   "description": "Infinix Inbook X1 Ci3 10th 16GB 256GB 14 Win10 Grey",
   "price": 5000.00,
   "stock": 50,
   "brand": "Infinix",
   "category": "LAPTOPS",
   "image": "https://i.dummyjson.com/data/products/9/1.jpg"
}
```
`status: 200` <br/><br/>

Erros comuns

```json
{
   "statusCode": "NOT_FOUND",
   "message": "Product not found",
   "dateTime": "02-12-2023 19:48:00"
}
```
```json
{
   "statusCode": "BAD_REQUEST",
   "message": "Other product already have this title and brand",
   "dateTime": "02-12-2023 19:51:34"
}
```
```json
{
   "statusCode": "UNAUTHORIZED",
   "message": "Invalid token",
   "dateTime": "02-12-2023 19:48:12"
}
```

</details>

<details>
<summary><b>DELETE products/id</b></summary>

Deletar um produto. O `id` deve ser enviado na url.

**Request**

| **Nome** | **Obrigatório** | **Tipo** | **Descrição**                    |
| :------- | :-------------- | :------- | :------------------------------- |
| id       | sim             | `string` | **Enviar via parâmetro de rota** |

> **_NOTA:_** É necessário enviar Token JWT via Authorization Header.

**Response**

Sucesso  
`no body returned for response` <br/> `status: 204` <br/>

Erros comums

```json
{
   "statusCode": "NOT_FOUND",
   "message": "Product not found",
   "dateTime": "02-12-2023 19:48:00"
}
```
```json
{
   "statusCode": "UNAUTHORIZED",
   "message": "Invalid token",
   "dateTime": "02-12-2023 19:48:12"
}
```

</details>

## 5. 👨‍💻 Autor
Criado por Daniel Justo

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/danielmjusto/)
[![github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/DanJusto)

Obrigado pela visita!