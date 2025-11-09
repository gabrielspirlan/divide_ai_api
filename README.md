# DivideAI API

API para realização de testes A/B com análise de eventos e estatísticas, gerenciamento de usuários, grupos e transações financeiras.

## 📋 Funcionalidades

### Autenticação e Segurança
- ✅ Autenticação JWT (JSON Web Token)
- ✅ Tokens sem expiração
- ✅ Senhas criptografadas com BCrypt
- ✅ Rotas públicas e protegidas
- ✅ Validação de email duplicado no cadastro

### Gerenciamento de Usuários
- ✅ Cadastro de usuários
- ✅ Login com email e senha
- ✅ Listagem de usuários (paginada)
- ✅ Atualização de dados do usuário
- ✅ Exclusão de usuários

### Gerenciamento de Grupos
- ✅ Criação de grupos
- ✅ Listagem de grupos (paginada)
- ✅ Busca de grupos por usuário
- ✅ Cálculo de totais de despesas por grupo
- ✅ Atualização e exclusão de grupos

### Gerenciamento de Transações
- ✅ Registro de transações financeiras
- ✅ Listagem de transações (paginada)
- ✅ Busca de transações por grupo
- ✅ Cálculo de totais de despesas por usuário
- ✅ Atualização e exclusão de transações

### Analytics e Eventos
- ✅ Registro de eventos (cliques, visualizações de página, loading)
- ✅ Estatísticas consolidadas (tempo médio de loading, total de cliques, total de page views)
- ✅ Histórico dos últimos eventos por tipo
- ✅ Identificação de elementos mais clicados e páginas mais acessadas

### Infraestrutura
- ✅ API REST com documentação Swagger
- ✅ Suporte completo ao Docker
- ✅ Banco de dados MongoDB
- ✅ Tratamento de erros com códigos HTTP apropriados
- ✅ Suporte a CORS

## 🚀 Início Rápido com Docker

### Pré-requisitos
- Docker
- MongoDB (local ou remoto)

### Executar com Docker

```bash
# Construir a imagem
docker build -t divideai-api .

# Executar o container
docker run -d \
  --name divideai-api \
  -p 8080:8080 \
  -e DATABASE_URI=mongodb://localhost:27017/divideai \
  divideai-api
```

**Serviços disponíveis:**
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger

## 🛠️ Desenvolvimento Local (sem Docker)

### Pré-requisitos
- Java 21+
- Maven 3.9+
- MongoDB 7.0+

### Configuração

1. **Instalar dependências:**
```bash
mvn clean install
```

2. **Configurar MongoDB:**
```bash
# Criar banco de dados
mongosh
use divideai
```

3. **Configurar variáveis de ambiente:**
```bash
export DATABASE_URI=mongodb://localhost:27017/divideai
export JWT_SECRET=ZGl2aWRlYWktc2VjcmV0LWtleS1jaGFuZ2UtaW4tcHJvZHVjdGlvbi1taW5pbXVtLTI1Ni1iaXRzLXJlcXVpcmVkLWZvci1oczI1Ni1hbGdvcml0aG0=
```

4. **Executar aplicação:**
```bash
mvn spring-boot:run
```

## 📡 Endpoints da API

### 🔐 Autenticação

| Método | Endpoint | Autenticação | Descrição |
|--------|----------|--------------|-----------|
| POST | `/auth/login` | ❌ Não | Login de usuário |

### 👥 Usuários

| Método | Endpoint | Autenticação | Descrição |
|--------|----------|--------------|-----------|
| POST | `/users` | ❌ Não | Cadastrar novo usuário |
| GET | `/users` | ✅ Sim | Listar todos os usuários (paginado) |
| GET | `/users/{id}` | ✅ Sim | Buscar usuário por ID |
| PUT | `/users/{id}` | ✅ Sim | Atualizar usuário |
| DELETE | `/users/{id}` | ✅ Sim | Deletar usuário |

### 👨‍👩‍👧‍👦 Grupos

| Método | Endpoint | Autenticação | Descrição |
|--------|----------|--------------|-----------|
| GET | `/groups` | ✅ Sim | Listar todos os grupos (paginado) |
| GET | `/groups/{id}` | ✅ Sim | Buscar grupo por ID |
| GET | `/groups/user/{userId}` | ✅ Sim | Buscar grupos por usuário (paginado) |
| GET | `/groups/{id}/totals` | ✅ Sim | Obter totais de despesas do grupo |
| POST | `/groups` | ✅ Sim | Criar novo grupo |
| PUT | `/groups/{id}` | ✅ Sim | Atualizar grupo |
| DELETE | `/groups/{id}` | ✅ Sim | Deletar grupo |

### 💰 Transações

| Método | Endpoint | Autenticação | Descrição |
|--------|----------|--------------|-----------|
| GET | `/transactions` | ✅ Sim | Listar todas as transações (paginado) |
| GET | `/transactions/{id}` | ✅ Sim | Buscar transação por ID |
| GET | `/transactions/group/{groupId}` | ✅ Sim | Buscar transações por grupo (paginado) |
| GET | `/transactions/user/{userId}/totals` | ✅ Sim | Obter totais de despesas do usuário |
| POST | `/transactions` | ✅ Sim | Criar nova transação |
| PUT | `/transactions/{id}` | ✅ Sim | Atualizar transação |
| DELETE | `/transactions/{id}` | ✅ Sim | Deletar transação |

### 📊 Eventos

| Método | Endpoint | Autenticação | Descrição |
|--------|----------|--------------|-----------|
| GET | `/event` | ❌ Não | Listar todos os eventos |
| GET | `/event/paginated` | ❌ Não | Listar eventos paginados |
| GET | `/event/{id}` | ❌ Não | Buscar evento por ID |
| POST | `/event` | ❌ Não | Criar novo evento |
| DELETE | `/event/{id}` | ❌ Não | Deletar evento |

### 📈 Analytics

| Método | Endpoint | Autenticação | Descrição |
|--------|----------|--------------|-----------|
| GET | `/event/analytics/stats` | ❌ Não | Estatísticas consolidadas |
| GET | `/event/analytics/average-loading-time` | ❌ Não | Tempo médio de loading |
| GET | `/event/analytics/total-clicks` | ❌ Não | Total de cliques |
| GET | `/event/analytics/total-page-views` | ❌ Não | Total de page views |
| GET | `/event/analytics/loading-history` | ❌ Não | Últimos 5 eventos de loading |
| GET | `/event/analytics/click-history` | ❌ Não | Últimos 5 cliques |
| GET | `/event/analytics/page-view-history` | ❌ Não | Últimas 5 page views |
| GET | `/event/analytics/slowest-loading-item` | ❌ Não | Item mais lento para carregar |
| GET | `/event/analytics/most-clicked-element` | ❌ Não | Elemento mais clicado |
| GET | `/event/analytics/most-accessed-page` | ❌ Não | Página mais acessada |

### 🏥 Health Check

| Método | Endpoint | Autenticação | Descrição |
|--------|----------|--------------|-----------|
| GET | `/health` | ❌ Não | Status da aplicação e banco de dados |
| GET | `/actuator/health` | ❌ Não | Health check do Spring Boot Actuator |

## 🔑 Autenticação JWT

### Como Usar

#### 1. Cadastrar um Usuário
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

**Resposta (201 CREATED):**
```json
{
  "id": "507f1f77bcf86cd799439011",
  "name": "João Silva",
  "email": "joao@example.com"
}
```

**Erro - Email Duplicado (409 CONFLICT):**
```json
{
  "error": "Email já cadastrado",
  "message": "Usuário já cadastrado com este email"
}
```

#### 2. Fazer Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

**Resposta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1MDdmMWY3N2JjZjg2Y2Q3OTk0MzkwMTEiLCJpYXQiOjE2OTUzMTIwMDB9.xyz...",
  "userId": "507f1f77bcf86cd799439011"
}
```

**Erro - Credenciais Inválidas (401 UNAUTHORIZED):**
```json
{
  "error": "Não autorizado",
  "message": "Credenciais inválidas"
}
```

#### 3. Acessar Rotas Protegidas
Use o token recebido no cabeçalho `Authorization`:

```bash
curl -X GET http://localhost:8080/users/507f1f77bcf86cd799439011 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1MDdmMWY3N2JjZjg2Y2Q3OTk0MzkwMTEiLCJpYXQiOjE2OTUzMTIwMDB9.xyz..."
```

**Erro - Token Ausente (401 UNAUTHORIZED):**
```json
{
  "error": "Não autorizado",
  "message": "Token JWT ausente ou inválido"
}
```

### Códigos de Status HTTP

| Código | Descrição | Quando Ocorre |
|--------|-----------|---------------|
| 200 OK | Sucesso | Requisição bem-sucedida |
| 201 CREATED | Criado | Recurso criado com sucesso |
| 401 UNAUTHORIZED | Não autorizado | Token ausente, inválido ou credenciais incorretas |
| 403 FORBIDDEN | Acesso negado | Usuário sem permissão para o recurso |
| 409 CONFLICT | Conflito | Email já cadastrado |
| 500 INTERNAL SERVER ERROR | Erro interno | Erro no servidor |

## 📝 Exemplos de Uso

### Criar Grupo
```bash
curl -X POST http://localhost:8080/groups \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-token>" \
  -d '{
    "name": "Viagem para a Praia",
    "description": "Despesas da viagem",
    "participants": ["507f1f77bcf86cd799439011", "507f1f77bcf86cd799439012"],
    "backgroundIconColor": "#FF5733"
  }'
```

### Criar Transação
```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-token>" \
  -d '{
    "description": "Jantar no restaurante",
    "value": 150.50,
    "participants": ["507f1f77bcf86cd799439011", "507f1f77bcf86cd799439012"],
    "group": "64f8a1b2c3d4e5f6a7b8c9d0",
    "date": "2025-11-09T19:30:00"
  }'
```

### Criar Evento
```bash
curl -X POST http://localhost:8080/event \
  -H "Content-Type: application/json" \
  -d '{
    "elementId": "button-signup",
    "variant": "A",
    "eventType": "CLICK",
    "page": "/signup",
    "loading": 150
  }'
```

**Resposta:**
```json
{
  "id": "64f8a1b2c3d4e5f6a7b8c9d0",
  "elementId": "button-signup",
  "variant": "A",
  "eventType": "CLICK",
  "page": "/signup",
  "loading": 150,
  "createdAt": "2025-09-21T10:30:00.123"
}
```

**Obter estatísticas consolidadas:**
```bash
curl http://localhost:8080/event/analytics/stats
```

**Resposta:**
```json
{
  "averageLoadingTime": 150.5,
  "loadingTimeUnit": "milliseconds",
  "totalClicks": 25,
  "totalPageViews": 50
}
```

**Verificar health check:**
```bash
curl http://localhost:8080/health
```

**Resposta:**
```json
{
  "status": "UP",
  "timestamp": "2025-09-15T19:30:00",
  "application": "DivideAI API",
  "version": "1.0.0",
  "database": {
    "status": "UP",
    "type": "MongoDB",
    "totalEvents": 25
  }
}
```

**Obter item que demorou mais tempo para carregar:**
```bash
curl http://localhost:8080/event/analytics/slowest-loading-item
```

**Resposta:**
```json
{
  "elementId": "heavy-component",
  "page": "/dashboard",
  "variant": "B",
  "loadingTime": 3500,
  "loadingTimeUnit": "milliseconds"
}
```

**Obter elemento mais clicado:**
```bash
curl http://localhost:8080/event/analytics/most-clicked-element
```

**Resposta:**
```json
{
  "elementId": "button-signup",
  "clickCount": 45,
  "variant": "A",
  "page": "/home"
}
```

**Obter página mais acessada:**
```bash
curl http://localhost:8080/event/analytics/most-accessed-page
```

**Resposta:**
```json
{
  "page": "/home",
  "accessCount": 120,
  "variant": "A"
}
```

## 🧪 Testes

### Executar testes

```bash
# Todos os testes
mvn test

# Testes específicos
mvn test -Dtest=EventServiceTest,EventControllerTest

# Com Docker
docker-compose exec divideai-api mvn test
```

### Cobertura de testes
- ✅ Testes unitários para Service
- ✅ Testes de integração para Controller
- ✅ Testes de endpoints com MockMvc
- ✅ 20+ testes implementados

## 📊 Modelo de Dados

### User (Usuário)
```json
{
  "id": "string",
  "name": "string",
  "email": "string",
  "password": "string (hash BCrypt)"
}
```

### Group (Grupo)
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "participants": ["userId1", "userId2"],
  "backgroundIconColor": "string (hex color)"
}
```

### Transaction (Transação)
```json
{
  "id": "string",
  "description": "string",
  "value": "number",
  "participants": ["userId1", "userId2"],
  "group": "groupId",
  "date": "2025-11-09T19:30:00"
}
```

### Event (Evento)
```json
{
  "id": "string",
  "elementId": "string",
  "variant": "A|B",
  "eventType": "CLICK|PAGE_VIEW|LOADING",
  "page": "string",
  "loading": "number (milliseconds)",
  "createdAt": "2025-09-21T10:30:00"
}
```

**Campos:**
- `id`: Identificador único do evento (gerado automaticamente)
- `elementId`: ID do elemento que foi acionado
- `variant`: Variação do teste A/B (A ou B)
- `eventType`: Tipo do evento (CLICK, PAGE_VIEW ou LOADING)
- `page`: Página onde o evento ocorreu
- `loading`: Tempo de carregamento em millisegundos (opcional)
- `createdAt`: Data e hora de criação do evento (definida automaticamente pelo backend)

### EventType (Enum)
- `CLICK` - Evento de clique
- `PAGE_VIEW` - Visualização de página
- `LOADING` - Evento de carregamento

## 🐳 Docker

### Comandos Docker úteis

```bash
# Build da imagem
docker build -t divideai-api .

# Executar container
docker run -d -p 8080:8080 -e DATABASE_URI=mongodb://host:27017/divideai divideai-api

# Ver logs
docker logs divideai-api

# Parar container
docker stop divideai-api
```

## 🔧 Configuração

### Variáveis de ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `DATABASE_URI` | URI de conexão MongoDB | `mongodb://localhost:27017/divideai` |
| `JWT_SECRET` | Chave secreta para assinatura JWT (Base64) | Chave padrão (alterar em produção) |

**⚠️ IMPORTANTE:** Sempre altere a chave `JWT_SECRET` em produção para garantir a segurança dos tokens JWT.

## 📁 Estrutura do Projeto

```
src/
├── main/java/com/api/divideai/
│   ├── event/
│   │   ├── application/
│   │   │   ├── dto/                    # DTOs para requests/responses
│   │   │   └── services/
│   │   │       ├── auth/               # Serviços de autenticação JWT
│   │   │       ├── event/              # Serviços de eventos
│   │   │       ├── group/              # Serviços de grupos
│   │   │       ├── transaction/        # Serviços de transações
│   │   │       └── user/               # Serviços de usuários
│   │   ├── config/                     # Configurações (Security, OpenAPI, etc)
│   │   ├── domain/
│   │   │   ├── collections/            # Entidades MongoDB
│   │   │   ├── dtos/                   # DTOs de domínio
│   │   │   │   ├── auth/               # DTOs de autenticação
│   │   │   │   ├── group/              # DTOs de grupos
│   │   │   │   ├── transaction/        # DTOs de transações
│   │   │   │   └── user/               # DTOs de usuários
│   │   │   └── enums/                  # Enumerações
│   │   ├── exceptions/                 # Exceções customizadas
│   │   ├── infrastructure/
│   │   │   └── repositories/           # Repositórios MongoDB
│   │   └── web/
│   │       └── controller/             # Controllers REST
│   ├── health/                         # Health check controller
│   └── DivideaiApplication.java
├── test/                               # Testes unitários e integração
└── resources/
    ├── application.properties
    └── application-docker.properties

docker/
├── Dockerfile
├── docker-compose.yml
├── run.sh                              # Script Linux/macOS
├── run.bat                             # Script Windows
└── README.md                           # Documentação Docker
```

## 🚀 Deploy

### Produção com Docker

```bash
# Build para produção
docker build -t divideai-api:prod .

# Executar com configurações de produção
docker run -d \
  --name divideai-api \
  -p 8080:8080 \
  -e DATABASE_URI="mongodb://prod-mongo:27017/divideai" \
  divideai-api:prod
```

## 📚 Documentação

- **Swagger UI**: http://localhost:8080/swagger

### Usando Swagger com Autenticação

1. Acesse o Swagger UI: http://localhost:8080/swagger
2. Clique no botão **Authorize** (cadeado no topo da página)
3. Insira o token JWT no formato: `Bearer <seu-token>`
4. Clique em **Authorize**
5. Agora você pode testar as rotas protegidas diretamente pelo Swagger

## 🔒 Segurança

### Autenticação JWT
- Tokens JWT são gerados no login e **não expiram**
- Tokens devem ser enviados no cabeçalho `Authorization` com o prefixo `Bearer`
- Senhas são criptografadas com BCrypt antes de serem armazenadas
- A chave secreta JWT deve ser alterada em produção

### Rotas Públicas vs Protegidas

**Rotas Públicas (sem autenticação):**
- `POST /auth/login` - Login
- `POST /users` - Cadastro de usuário
- `/event/**` - Todos os endpoints de eventos e analytics
- `/swagger-ui/**` - Documentação Swagger
- `/health/**` - Health check

**Rotas Protegidas (requerem token JWT):**
- `GET /users`, `GET /users/{id}`, `PUT /users/{id}`, `DELETE /users/{id}`
- `/groups/**` - Todos os endpoints de grupos
- `/transactions/**` - Todos os endpoints de transações

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
