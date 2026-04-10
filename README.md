# 🚀 Automação de Testes API - Trello

Projeto de automação de testes de API utilizando **RestAssured + Cucumber + Java**, com foco em validação de cenários funcionais e não funcionais da API do Trello.

---

## 📌 Objetivo

Validar o comportamento da API de Cards do Trello cobrindo:

- CRUD completo
- Cenários positivos e negativos
- Autenticação (token e key)
- Validação de contratos
- Tratamento de erros inconsistentes da API

---

## 🧪 Tecnologias Utilizadas

- Java 17
- RestAssured
- Cucumber (BDD)
- JUnit
- Maven
- Allure Report
- GitHub Actions (CI/CD)

---

## 🧠 Estratégia de Testes

### ✔ Testes Positivos
- Criação de card
- Atualização de card
- Exclusão de card

### ❌ Testes Negativos
- IDs inválidos
- Campos fora do padrão
- Payload inválido

### 🔐 Autenticação
- Token inválido → 401
- Key inválida → 401
- Parâmetros ausentes → 401

---

## ⚠️ Tratamento de Inconsistências da API

A API do Trello retorna respostas em formatos diferentes:

- JSON:
```json
{
  "message": "Invalid objectId"
}