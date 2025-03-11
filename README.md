# 🏦 Financial Monitoring Platform

## 📌 Visão Geral
A **Financial Monitoring Platform** é um sistema distribuído baseado em microsserviços que monitora transações financeiras em tempo real para detectar atividades fraudulentas. Ele utiliza **Spring Boot, Apache Kafka, PostgreSQL, MongoDB, Docker e Spring Cloud Gateway** para fornecer escalabilidade e alta disponibilidade.

## 📐 Arquitetura
A plataforma é composta pelos seguintes microsserviços:

1. **API Gateway (`api-gateway`)**
   - Atua como porta de entrada para os clientes e roteia as requisições para os microsserviços.
   - Gerencia autenticação e autorização via **JWT**.
   - Implementa **Rate Limiting** e **Circuit Breaker** para resiliência.

2. **User Service (`user-service`)**
   - Gerencia usuários e autenticação.
   - Responsável pelo registro e login.
   - Utiliza **PostgreSQL** para armazenamento de usuários.

3. **Transaction Service (`transaction-service`)**
   - Registra e processa transações financeiras.
   - Publica eventos de transação para o **Kafka**.
   - Utiliza **PostgreSQL** para armazenar transações.

4. **Fraud Detection Service (`fraud-service`)**
   - Consome eventos de transação do **Kafka**.
   - Aplica regras de negócio e algoritmos de detecção de fraude.
   - Registra fraudes detectadas no **MongoDB**.

5. **Notification Service (`notification-service`)**
   - Envia notificações para usuários em caso de fraude detectada.
   - Pode se comunicar via **e-mail, SMS ou push notifications**.

## 🛠️ Tecnologias Utilizadas
- **Backend:** Java, Spring Boot, Spring Cloud, Spring Security
- **Mensageria:** Apache Kafka
- **Banco de Dados:** PostgreSQL (relacional) & MongoDB (documento)
- **Autenticação:** JWT (JSON Web Token)
- **Containerização:** Docker & Docker Compose

## 🚀 Como Executar o Projeto

### 🐳 1. Clonar o Repositório e Configurar as Dependências
```sh
git clone https://github.com/seu-usuario/financial-monitoring-platform.git
cd financial-monitoring-platform
```

## 🔧 2. Configurar o Arquivo .env
Crie um arquivo .env na raiz do projeto e defina as variáveis de ambiente, como credenciais do banco e do Kafka.

📦 3. Subir a Infraestrutura com Docker Compose
```
docker-compose up -d
```
Isso iniciará os containers do PostgreSQL, MongoDB, Kafka, Zookeeper e os microsserviços.

## 📡 Comunicação entre Microsserviços

- API Gateway → User Service (Autenticação via REST)
- Transaction Service → Kafka (Produz eventos de transação)
- Fraud Service → Kafka (Consome eventos de transação e detecta fraudes)
- Fraud Service → Notification Service (Envia notificações de fraude)
