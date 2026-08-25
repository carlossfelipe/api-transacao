# 🚀 API de Transações

API RESTful desenvolvida em **Java 17** com **Spring Boot** para gerenciamento de contas e processamento de transações financeiras entre usuários.

O projeto implementa regras de negócio para controle de saldo, validação de contas e usuários, registro de transações e consulta de estatísticas financeiras.

---

## 📌 Sumário

* [Sobre o Projeto](#-sobre-o-projeto)
* [Tecnologias](#-tecnologias)
* [Arquitetura](#-arquitetura)
* [Estrutura do Projeto](#-estrutura-do-projeto)
* [Modelo de Dados](#-modelo-de-dados)
* [Regras de Negócio](#-regras-de-negócio)
* [Endpoints](#-endpoints)
* [Tratamento de Exceções](#-tratamento-de-exceções)
* [Como Executar](#-como-executar)
* [Testes](#-testes)
* [Desenvolvedor](#-desenvolvedor)

---

## 📖 Sobre o Projeto

A **API de Transações** é uma aplicação REST desenvolvida para simular operações de transferência financeira entre contas.

O sistema permite:

* Realizar transferências entre contas;
* Validar a existência de usuários e contas;
* Verificar saldo antes de realizar uma transferência;
* Registrar transações;
* Consultar o histórico de transações;
* Consultar estatísticas das transações;
* Calcular valores mínimo, máximo, médio e total;
* Tratar exceções de negócio de forma centralizada.

O projeto foi desenvolvido com uma arquitetura baseada na separação de responsabilidades entre **Controller, Service, Repository, Entity e DTO**.

---

## 🛠 Tecnologias

| Tecnologia                   | Utilização                            |
| ---------------------------- | ------------------------------------- |
| **Java 17**                  | Linguagem de programação              |
| **Spring Boot**              | Framework principal                   |
| **Spring Web**               | Desenvolvimento da API REST           |
| **Spring Data JPA**          | Persistência e acesso ao banco        |
| **Hibernate**                | ORM                                   |
| **PostgreSQL**               | Banco de dados                        |
| **Maven**                    | Gerenciamento de dependências e build |
| **JUnit / Spring Boot Test** | Testes automatizados                  |

---

## 🏗 Arquitetura

A aplicação utiliza uma arquitetura em camadas:

```text
Cliente
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
PostgreSQL
```

### Responsabilidades

**Controller**

Responsável por receber as requisições HTTP, validar os dados de entrada necessários e retornar as respostas da API.

**Service**

Contém as regras de negócio da aplicação, como validação de saldo e processamento das transferências.

**Repository**

Responsável pela comunicação com o banco de dados utilizando Spring Data JPA.

**Entity**

Representa as entidades persistidas no banco de dados.

**DTO**

Define os objetos utilizados para entrada e saída de dados da API, evitando expor diretamente as entidades.

**Exception**

Contém as exceções específicas das regras de negócio e o tratamento global dessas exceções.

---

## 📂 Estrutura do Projeto

```text
src/
├── main/
│   ├── java/
│   │   └── com/github/carlossfelipe/api_transacao/
│   │       ├── ApiTransacaoApplication.java
│   │       │
│   │       ├── controller/
│   │       │   └── TransacaoController.java
│   │       │
│   │       ├── dto/
│   │       │   ├── ErroResponse.java
│   │       │   ├── TransacaoEstatisticaResponseDTO.java
│   │       │   ├── TransacaoRequestDTO.java
│   │       │   └── TransacaoResponseDTO.java
│   │       │
│   │       ├── entity/
│   │       │   ├── Conta.java
│   │       │   ├── Transacao.java
│   │       │   └── Usuario.java
│   │       │
│   │       ├── exception/
│   │       │   ├── ContaNaoEncontradaException.java
│   │       │   ├── NenhumaTransacaoException.java
│   │       │   ├── SaldoInsuficienteException.java
│   │       │   ├── TransacaoInvalidaException.java
│   │       │   ├── UsuarioNaoEncontradoException.java
│   │       │   └── handler/
│   │       │       └── GlobalExceptionHandler.java
│   │       │
│   │       ├── repository/
│   │       │   ├── ContaRepository.java
│   │       │   ├── TransacaoRepository.java
│   │       │   └── UsuarioRepository.java
│   │       │
│   │       └── service/
│   │           └── TransacaoService.java
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/
        └── com/github/carlossfelipe/api_transacao/
            └── ApiTransacaoApplicationTests.java
```

> ⚠️ O arquivo `application.properties` contém configurações locais do banco de dados e não deve ser versionado quando possuir credenciais reais.

---

## 🗄 Modelo de Dados

O sistema possui três entidades principais:

```text
Usuario
   │
   │ 1 : 1
   ▼
Conta
   │
   │
   │
Transacao
  ▲     ▲
  │     │
remetente
destinatario
```

### 👤 Usuario

Representa o usuário da aplicação.

Principais atributos:

* `id`
* `nome`
* `conta`
* `transacoesEnviadas`
* `transacoesRecebidas`

### 💳 Conta

Representa a conta financeira do usuário.

Principais atributos:

* `id`
* `agencia`
* `chaveTransacao`
* `saldo`
* `usuario`

Cada usuário possui uma conta associada.

A `chaveTransacao` é única no sistema.

### 💸 Transacao

Representa uma transferência realizada entre duas contas.

Uma transação possui:

* Remetente;
* Destinatário;
* Valor;
* Data da transação.

---

## 📋 Regras de Negócio

### 1. Validação de usuário

O sistema verifica se os usuários envolvidos na operação existem.

Caso contrário:

```text
UsuarioNaoEncontradoException
```

### 2. Validação de conta

As contas de origem e destino precisam existir para que a transferência seja realizada.

Caso contrário:

```text
ContaNaoEncontradaException
```

### 3. Saldo insuficiente

Antes da transferência, o saldo da conta remetente é verificado.

Se o saldo for menor que o valor da transação, a operação é interrompida.

```text
SaldoInsuficienteException
```

### 4. Validação da transação

Valores inválidos ou dados inconsistentes não podem ser processados.

```text
TransacaoInvalidaException
```

### 5. Estatísticas

O sistema permite consultar estatísticas das transações registradas, incluindo:

* Quantidade de transações;
* Soma dos valores;
* Média;
* Maior valor;
* Menor valor.

---

# 🔌 Endpoints

## 💸 Realizar transferência

### `POST /transacao`

Realiza uma transferência entre duas contas.

### Requisição

```json
{
  "remetente": "joao",
  "valor": 250.00,
  "chaveTransacao": "maria@pix.com"
}
```

### Resposta — sucesso

**HTTP 201 Created**

```json
{
  "id": "b833e532-a402-4ad0-ad57-1e48c004144f",
  "remetente": "joao",
  "destinatario": "maria",
  "valor": 250.00,
  "data": "2026-03-30T14:30:00"
}
```

---

## 📄 Listar transações

### `GET /transacao`

Retorna as transações registradas.

### Resposta — sucesso

**HTTP 200 OK**

```json
[
  {
    "id": "b833e532-a402-4ad0-ad57-1e48c004144f",
    "remetente": "joao",
    "destinatario": "maria",
    "valor": 250.00,
    "data": "2026-03-30T14:30:00"
  },
  {
    "id": "6c0e1d72-5d4c-4f0d-91d6-0e0b6a7a8f21",
    "remetente": "pedro",
    "destinatario": "maria",
    "valor": 50.00,
    "data": "2026-03-30T15:00:00"
  }
]
```

---

## 📊 Consultar estatísticas

### `GET /transacao/media`

Retorna estatísticas referentes às transações registradas.

### Resposta — sucesso

**HTTP 200 OK**

```json
{
  "max": 250.00,
  "min": 50.00,
  "avg": 150.00
}
```

---

# ⚠️ Tratamento de Exceções

As exceções da aplicação são tratadas de forma centralizada pelo:

```text
GlobalExceptionHandler
```

O objetivo é evitar que o cliente receba informações internas da aplicação, como stack traces.

### Formato da resposta

```json
{
  "status": 404,
  "error": "Conta não encontrada",
  "message": "Conta não encontrada"
}
```

### Exceções mapeadas

| Exceção                         |  HTTP | Descrição                                        |
| ------------------------------- | ----: | ------------------------------------------------ |
| `UsuarioNaoEncontradoException` | `404` | Usuário não encontrado                           |
| `ContaNaoEncontradaException`   | `404` | Conta não encontrada                             |
| `SaldoInsuficienteException`    | `422` | Saldo insuficiente para realizar a transferência |
| `TransacaoInvalidaException`    | `400` | Dados da transação inválidos                     |
| `NenhumaTransacaoException`     | `404` | Nenhuma transação encontrada                     |

---

# ⚙️ Como Executar

## Pré-requisitos

Antes de executar o projeto, instale:

* **Java 17 ou superior**
* **PostgreSQL**
* **Maven 3.8+** (opcional, pois o projeto possui Maven Wrapper)

### 1. Clone o repositório

```bash
git clone https://github.com/carlossfelipe/api_transacao.git
```

### 2. Acesse o projeto

```bash
cd api_transacao
```

### 3. Configure o banco de dados

Crie um banco PostgreSQL:

```sql
CREATE DATABASE api_transacoes;
```

Configure as credenciais do banco no arquivo local `application.properties`.

> Não envie credenciais ou senhas para o Git.

### 4. Execute o projeto

Utilizando Maven:

```bash
mvn spring-boot:run
```

Ou utilizando o Maven Wrapper:

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

---

# 🧪 Testes

Os testes estão localizados em:

```text
src/test/
```

Para executar os testes:

```bash
mvn test
```

Ou utilizando o Maven Wrapper:

```bash
./mvnw test
```

No Windows:

```bash
mvnw.cmd test
```

---

# 🔐 Segurança

As credenciais utilizadas para conexão com o banco de dados devem ser mantidas fora do controle de versão.

O arquivo:

```text
application.properties
```

está configurado no `.gitignore` para evitar o envio acidental de credenciais ao repositório.

---

# 🚧 Possíveis melhorias

Algumas funcionalidades que podem ser adicionadas futuramente:

* [ ] Autenticação e autorização com Spring Security;
* [ ] JWT para autenticação;
* [ ] Documentação com Swagger/OpenAPI;
* [ ] Paginação do histórico de transações;
* [ ] Testes unitários adicionais;
* [ ] Testes de integração;
* [ ] Dockerização da aplicação;
* [ ] Docker Compose para API + PostgreSQL;
* [ ] Pipeline de CI/CD;
* [ ] Logs estruturados;
* [ ] Monitoramento da aplicação.

---

# 👨‍💻 Desenvolvedor

**Carlos Felipe**

Projeto desenvolvido para fins de estudo e prática de desenvolvimento de APIs REST com **Java e Spring Boot**.

---

## 📄 Licença

Este projeto é destinado a fins educacionais e de portfólio.
