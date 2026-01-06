# Security API

Este projeto é uma API de autenticação utilizando **Spring Boot 3**, **Spring Security 6**, **JWT**, e **JPA/Hibernate**.

Ele oferece endpoints para **login**, **registro de usuários**, e proteção de rotas com validação de token via filtro.

---

## 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3**
* **Spring Security 6**
* **JWT (Auth0 java-jwt)**
* **JPA/Hibernate**
* **Bean Validation (Jakarta)**
* **BCryptPasswordEncoder**

---

## 📦 Funcionalidades

* Registro de usuário
* Login com e-mail e senha
* Geração de token JWT
* Validação de token via filtro (stateless)
* Rotas públicas e privadas
* Autenticação via `UserDetailsService`

---

## 📁 Estrutura do Projeto

```
assum.security
├── config
│   ├── AuthConfig.java
│   ├── JWTUserData.java
│   ├── SecurityConfig.java
│   ├── SecurityFilter.java
│   └── TokenConfig.java
├── controller
│   ├── AuthController.java
│   └── TestController.java
├── dto
│   ├── request
│   └── response
├── entity
│   └── User.java
├── repository
│   └── UserRepository.java
└── SecurityApplication.java
```

---

## 🔑 Endpoints

### **POST /auth/register**

Registra um novo usuário.

```json
{
  "name": "João",
  "email": "joao@email.com",
  "password": "123456"
}
```

### **POST /auth/login**

Realiza login e retorna um token JWT.

```json
{
  "email": "joao@email.com",
  "password": "123456"
}
```

**Resposta:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

### **GET /test** (rota protegida)

Requer Header:

```
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## ⚙️ Configuração de Segurança

* **Stateless** (sem sessão)
* CORS habilitado
* `/auth/login` e `/auth/register` são públicas
* Todas as outras rotas são protegidas

O `SecurityFilter` valida tokens antes do processamento da requisição.

---

## 🔐 Geração e Validação do Token

A classe `TokenConfig` utiliza `HMAC256`.

O token contém:

* `UserId`
* `subject` = e-mail
* `expires_at`
* `issued_at`

---

## 🧪 Como Rodar

1. Clonar o repositório
2. Configurar banco de dados no `application.properties`
3. Rodar a aplicação:

```
docker compose up -d
mvn spring-boot:run
```

4. Testar no Insomnia/Postman os endpoints de login/registro

---

## 📌 Melhorias Futuras

* Implementação de roles e authorities
* Refresh token
* Melhor tratamento de erros
* Testes unitários e de integração
* Documentação Swagger/OpenAPI

---

## 📄 Licença

Projeto livre para estudo e colaboração.

