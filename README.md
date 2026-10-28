# 📚 API de Gestão de Biblioteca

API REST desenvolvida em **Java 21** e **Spring Boot** para gerenciar o acervo de uma biblioteca: **livros**, **membros** e **empréstimos**, com autenticação via **JWT**.

> Projeto desenvolvido para praticar arquitetura back-end, segurança de APIs e regras de negócio com Spring Boot.

## ✨ Funcionalidades

- ✅ Cadastro, login e logout de membros com **token JWT** (válido por 2 horas)
- ✅ CRUD completo de **livros** e **membros**
- ✅ **Empréstimos**: retirada, devolução, empréstimos ativos e histórico por membro e por livro
- ✅ Regra de negócio que impede emprestar um livro que já está emprestado
- ✅ **DTOs** e **mappers** separando a API do modelo de dados
- ✅ Validação de dados e **tratamento global de exceções**
- ✅ Documentação interativa com **Swagger/OpenAPI**

## 🛠️ Tecnologias

- **Java 21**
- **Spring Boot**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Validation
- **JWT** (java-jwt)
- **SpringDoc OpenAPI** (Swagger UI)
- **H2 Database** (em memória)
- **Maven**

## 📋 Pré-requisitos

- JDK 21 ou superior
- Maven 3.9+ (ou use o Maven Wrapper `./mvnw`)
- Git

## 🚀 Como rodar localmente

```bash
git clone https://github.com/AndrewGRM/Sistema-gerenciamento-biblioteca.git
cd Sistema-gerenciamento-biblioteca
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

- **Swagger UI:** http://localhost:8080/swagger-ui.html

## 🔒 Autenticação

Todas as rotas, exceto `/api/auth/**` e a documentação do Swagger, exigem token.

1. Cadastre um membro em `POST /api/auth/register`
2. Faça login em `POST /api/auth/login` e copie o `token` da resposta
3. Envie o token nas próximas requisições pelo cabeçalho:

```
Authorization: Bearer <seu_token>
```

**Exemplo de login:**

```json
{
  "email": "usuario@email.com",
  "password": "sua_senha"
}
```

## 📡 Endpoints

### Autenticação — `/api/auth`
| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/auth/register` | Cadastra um novo membro |
| POST | `/api/auth/login` | Autentica e retorna o token JWT |
| POST | `/api/auth/logout` | Logout (o token deve ser descartado pelo cliente) |

### Livros — `/api/books`
| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/books` | Cadastra um livro |
| GET | `/api/books` | Lista todos os livros |
| GET | `/api/books/{id}` | Busca um livro por ID |
| PUT | `/api/books/{id}` | Atualiza um livro |
| DELETE | `/api/books/{id}` | Remove um livro |

### Membros — `/api/members`
| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/members` | Cadastra um membro |
| GET | `/api/members` | Lista todos os membros |
| GET | `/api/members/{id}` | Busca um membro por ID |
| PUT | `/api/members/{id}` | Atualiza um membro |
| DELETE | `/api/members/{id}` | Remove um membro |

### Empréstimos — `/api/loans`
| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/loans/borrow` | Registra um empréstimo (`bookId`, `memberId`) |
| PUT | `/api/loans/{id}/return` | Registra a devolução |
| GET | `/api/loans` | Lista todos os empréstimos |
| GET | `/api/loans/active` | Lista os empréstimos ativos |
| GET | `/api/loans/member/{memberId}` | Histórico de um membro |
| GET | `/api/loans/book/{bookId}` | Histórico de um livro |

## 📁 Estrutura do projeto

```
src/main/java/com/biblioteca/gerenciador_biblioteca_api/
├── config/        # Carga inicial de dados
├── controller/    # Controladores REST (livros, membros, empréstimos, auth)
├── dto/           # Objetos de requisição e resposta
├── exception/     # Exceções personalizadas e handler global
├── model/         # Entidades JPA (Book, Member, Loan)
├── repository/    # Repositórios Spring Data
├── security/      # Configuração do Spring Security e filtro JWT
├── service/       # Regras de negócio, mappers e serviços de autenticação
└── util/          # Constantes da API
```

## ⚠️ Tratamento de erros

| Status | Situação |
|---|---|
| 400 Bad Request | Dados inválidos ou tentativa de emprestar um livro que já está emprestado |
| 401/403 | Requisição sem token ou com token inválido |
| 404 Not Found | Livro, membro ou empréstimo não encontrado |

---

Desenvolvido por **Andrew Ramos** · [LinkedIn](https://www.linkedin.com/in/andrewgrm/) · [GitHub](https://github.com/AndrewGRM)
