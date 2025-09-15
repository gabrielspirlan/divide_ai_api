# DivideAI API

API para realização de testes A/B com análise de eventos e estatísticas.

## 📋 Funcionalidades

- ✅ Registro de eventos (cliques, visualizações de página, loading)
- ✅ Estatísticas consolidadas (tempo médio de loading, total de cliques, total de page views)
- ✅ Histórico dos últimos eventos por tipo
- ✅ API REST com documentação Swagger
- ✅ Suporte completo ao Docker
- ✅ Banco de dados MongoDB
- ✅ Testes unitários e de integração

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
```

4. **Executar aplicação:**
```bash
mvn spring-boot:run
```

## 📡 Endpoints da API

### Eventos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/event` | Listar todos os eventos |
| GET | `/event/{id}` | Buscar evento por ID |
| POST | `/event` | Criar novo evento |
| DELETE | `/event/{id}` | Deletar evento |

### Analytics

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/event/analytics/stats` | **Estatísticas consolidadas** |
| GET | `/event/analytics/average-loading-time` | Tempo médio de loading |
| GET | `/event/analytics/total-clicks` | Total de cliques |
| GET | `/event/analytics/total-page-views` | Total de page views |
| GET | `/event/analytics/loading-history` | Últimos 5 eventos de loading |
| GET | `/event/analytics/click-history` | Últimos 5 cliques |
| GET | `/event/analytics/page-view-history` | Últimas 5 page views |

### Exemplo de Uso

**Criar evento:**
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

### Event
```json
{
  "id": "string",
  "elementId": "string",
  "variant": "A|B",
  "eventType": "CLICK|PAGE_VIEW|LOADING",
  "page": "string",
  "loading": "number (milliseconds)"
}
```

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

## 📁 Estrutura do Projeto

```
src/
├── main/java/com/api/divideai/
│   ├── event/
│   │   ├── application/
│   │   │   ├── dto/          # DTOs para requests/responses
│   │   │   └── services/     # Lógica de negócio
│   │   ├── domain/
│   │   │   ├── collections/  # Entidades MongoDB
│   │   │   └── enums/        # Enumerações
│   │   ├── infrastructure/
│   │   │   └── repositories/ # Repositórios MongoDB
│   │   └── web/
│   │       └── controller/   # Controllers REST
│   └── DivideaiApplication.java
├── test/                     # Testes unitários e integração
└── resources/
    ├── application.properties
    └── application-docker.properties

docker/
├── Dockerfile
├── docker-compose.yml
├── run.sh                    # Script Linux/macOS
├── run.bat                   # Script Windows
└── README.md                 # Documentação Docker
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

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
