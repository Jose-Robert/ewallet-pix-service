# 💳 ewallet-pix-service

API de carteira digital com suporte a Pix, garantindo **consistência sob concorrência**, **idempotência**, **taxas de transferência seguras** (rate limit) e **operações financeiras confiáveis**.

---

## 📘 Sumário
- [Sobre o Projeto](#-sobre-o-projeto)
- [Arquitetura e Padrões](#-arquitetura-e-padrões)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Como Executar](#-como-executar)
- [Acesso Rápido](#-acesso-rápido)
- [Endpoints](#-endpoints)
- [Autor](#-autor)

---

## 📌 Sobre o Projeto

O **ewallet-pix-service** é um microserviço de carteira digital que oferece:

- Criação e gerenciamento de carteiras.  
- Registro de chaves Pix.  
- Depósitos, saques e consultas de saldo.  
- Transferências Pix internas.  
- Webhook simulando confirmações/negações Pix.  
- Controle de concorrência e idempotência para evitar duplicidade de transações.  
- Rate Limit para proteger operações sensíveis.

---

## 🏛 Arquitetura e Padrões

O serviço adota boas práticas como:

- **Arquitetura em camadas**  
- **Validação idempotente** para transações e webhooks  
- **Rate Limiting (Bucket4j)** para proteger endpoints críticos  
- **Padrão Specification (JPA)** para filtros dinâmicos  
- **Concorrência controlada** no saldo/transferências  
- **Swagger/OpenAPI** para documentação  
- Preparado para execução em **containers Docker**

---

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **PostgreSQL**
- **Hibernate / JPA / Specification**
- **Lombok**
- **Docker & Docker Compose**
- **Bucket4j (Rate Limiter)**
- **Swagger / OpenAPI 3**
- **PGAdmin**

---

## ▶️ Como Executar

### 🔧 Pré-requisitos
- Docker e Docker Compose  
- JDK 17  
- Maven 3+  

---

### ▶️ 1. Subir tudo via Docker
- /{BASE_DIRECTORY}/ewallet-pix-service
```bash
docker compose up --build
```
Ou 

```bash
docker compose up -d
```

### ▶️ 2. Executar localmente
- /{BASE_DIRECTORY}/ewallet-pix-service
```bash
mvn spring-boot:run
```

### ▶️ 3. Baixar imagem publicada - DockerHub e executar via .sh
```bash
docker pull jsrobert10/ewallet-pix-service:1.0.0
```

- /{BASE_DIRECTORY}/ewallet-pix-service
```bash
chmod +x run_ewallet.sh
./run_ewallet.sh
```

---

## 🌐 Acesso Rápido
| Recurso    | URL                                                                                                      |
| ---------- | -------------------------------------------------------------------------------------------------------- |
| Swagger UI | [http://localhost:8080/api/v1/swagger-ui/index.html](http://localhost:8080/api/v1/swagger-ui/index.html) |
| PGAdmin    | [http://localhost:4000/](http://localhost:4000/)                                                         |

---

## 📡 Endpoints
| Método   | Endpoint                        | Descrição                              |
| -------- | ------------------------------- | -------------------------------------- |
| **POST** | `/wallets`                      | Cria uma nova carteira                 |
| **POST** | `/wallets/pix-keys`             | Registra uma chave Pix na carteira     |
| **POST** | `/wallets/deposits`             | Deposita um valor na carteira          |
| **POST** | `/wallets/withdraw`             | Realiza um saque da carteira           |
| **GET**  | `/wallets/{walletId}/balance`   | Consulta saldo atual                   |
| **GET**  | `/wallets/transactions/balance` | Consulta saldo histórico por timestamp |

| Método   | Endpoint         | Descrição                                 |
| -------- | ---------------- | ----------------------------------------- |
| **POST** | `/pix/transfers` | Envia transferência Pix                   |
| **POST** | `/pix/webhook`   | Recebe eventos Pix (CONFIRMED / REJECTED) |

---

## 👤 Autor

José Robert
- GitHub: https://github.com/Jose-Robert ![GitHub](https://img.shields.io/badge/GitHub-000?logo=github&logoColor=white)
- DockerHub: jsrobert10/ewallet-pix-service ![DockerHub](https://img.shields.io/badge/DockerHub-0db7ed?logo=docker&logoColor=white)
- LinkdIn: https://www.linkedin.com/in/joserobertgoncalves/ ![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?logo=linkedin&logoColor=white)

