# Simulador de Banco Simplificado

A ideia principal deste projeto é desenvolver uma API REST que simula algumas das principais operações de um banco digital.

O projeto foi criado com fins de estudo e tem como objetivo aplicar, na prática, conceitos de Java, Spring Boot, segurança, autenticação, regras de negócio, concorrência e conteinerização com Docker.

> [!NOTE]
> Projeto em desenvolvimento. Novas funcionalidades, regras de negócio e melhorias de arquitetura ainda serão adicionadas.

---

## Objetivo

Este é um projeto pessoal de estudo criado para praticar conceitos de back-end, como:

- Modelagem de domínio
- APIs REST
- Regras de negócio
- Tratamento global de exceções
- Autenticação e autorização
- Spring Security
- JWT
- Hash de senhas com BCrypt
- Controle de acesso por tipo de usuário
- Transações e controle de concorrência
- Integração com serviços externos
- Docker
- Docker Compose
- Variáveis de ambiente

---

## Tecnologias utilizadas

- Java 21
- Spring Boot 4
- Spring MVC
- Spring Security
- JWT (JJWT)
- Bean Validation
- BCrypt
- Maven
- Lombok
- Docker
- Docker Compose

---

## Funcionalidades

- [x] Cadastro de clientes e lojistas
- [x] Validação de CPF e e-mails únicos
- [x] Autenticação de usuários
- [x] Geração e validação de JWT
- [x] Proteção de endpoints com Spring Security
- [x] Autorização baseada no tipo de usuário
- [x] Consulta de saldo
- [x] Listagem de transações do usuário autenticado
- [x] Transferência de dinheiro entre usuários
- [x] Restrição para que lojistas apenas recebam dinheiro
- [x] Validação de saldo antes da transferência
- [x] Consulta a um serviço externo de autorização antes de concluir a transferência
- [x] Reversão da operação em caso de falha
- [x] Notificação assíncrona ao destinatário após uma transferência
- [x] Tratamento global de exceções
- [x] Conteinerização da aplicação com Docker
- [x] Execução da aplicação com Docker Compose

---

## Regras de negócio

- Usuários possuem nome completo, CPF, e-mail e senha.
- Existem dois tipos de usuário: **CLIENTE** e **LOJISTA**.
- Clientes podem enviar e receber transferências.
- Lojistas podem receber transferências, mas não podem enviá-las.
- Toda transferência passa por validação de saldo.
- O remetente de uma operação é identificado pelo usuário autenticado.
- Endpoints protegidos exigem um JWT válido.
- O acesso a determinadas operações depende do tipo de usuário.
- Antes de finalizar uma transferência, o sistema consulta um serviço externo simulado de autorização.
- Em caso de inconsistência durante uma transferência, a operação deve ser revertida.

---

## Segurança

A aplicação utiliza **Spring Security** com autenticação baseada em **JWT**.

Após realizar o login, a API gera um token que deve ser enviado nas requisições protegidas através do header:

```http
Authorization: Bearer SEU_TOKEN
```

As senhas dos usuários não são armazenadas em texto puro e são protegidas utilizando BCrypt.

Existem endpoints com permissões diferentes para `CLIENTE` e `LOJISTA`.

> [!IMPORTANT]
> Algumas rotas podem permanecer públicas propositalmente para facilitar testes e demonstrações do projeto. Esses casos são identificados no próprio código.

---

## Configuração das variáveis de ambiente

O segredo utilizado para assinar os tokens JWT não é armazenado diretamente no código-fonte.

Crie um arquivo `.env` na raiz do projeto:

```env
JWT_SECRET=adicione-aqui-uma-chave-secreta-com-pelo-menos-32-caracteres
JWT_EXPIRATION=86400000
```

A estrutura ficará semelhante a:

```text
simulador/
├── .env
├── compose.yaml
├── Dockerfile
├── pom.xml
└── src/
```

O `application.properties` utiliza essas configurações:

```properties
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION:86400000}
```

> [!WARNING]
> O arquivo `.env` contém informações sensíveis e não deve ser enviado ao GitHub. Ele está incluído no `.gitignore`.

Um arquivo `.env.example` pode ser utilizado como referência:

```env
JWT_SECRET=adicione-uma-chave-segura
JWT_EXPIRATION=86400000
```

---

# Como executar

Existem duas formas principais de executar o projeto:

1. Diretamente com Java/Maven
2. Utilizando Docker

---

## Opção 1 — Executando com Java e Maven

### Pré-requisitos

Certifique-se de possuir:

- Java 21
- Maven, ou utilize o Maven Wrapper incluído no projeto

Verifique o Java:

```bash
java --version
```

### 1. Clone o repositório

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta:

```bash
cd simulador
```

### 2. Configure o `.env`

Crie o arquivo:

```text
.env
```

E adicione:

```env
JWT_SECRET=adicione-aqui-uma-chave-secreta-com-pelo-menos-32-caracteres
JWT_EXPIRATION=86400000
```

### 3. Execute a aplicação

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux/macOS/Git Bash:

```bash
./mvnw spring-boot:run
```

Caso esteja utilizando uma instalação própria do Maven:

```bash
mvn spring-boot:run
```

Após a inicialização, a API estará disponível em:

```text
http://localhost:8080
```

---

# Executando com Docker

Utilizar Docker evita a necessidade de instalar Java e Maven diretamente na máquina que executará a aplicação.

É necessário apenas possuir o Docker instalado e em execução.

## Utilizando Docker Compose

Esta é a forma recomendada para executar o projeto em um ambiente Docker.

Na raiz do projeto, execute:

```bash
docker compose up --build
```

O Compose irá:

1. Ler o `compose.yaml`
2. Construir a imagem utilizando o `Dockerfile`
3. Compilar a aplicação
4. Carregar as variáveis necessárias
5. Criar o contêiner
6. Expor a aplicação na porta `8080`

A API ficará disponível em:

```text
http://localhost:8080
```

### Executar em segundo plano

```bash
docker compose up --build -d
```

### Visualizar os contêineres

```bash
docker compose ps
```

### Acompanhar os logs

```bash
docker compose logs -f
```

### Parar a aplicação

```bash
docker compose stop
```

### Parar e remover os contêineres criados pelo Compose

```bash
docker compose down
```

O `docker compose down` não remove, por padrão, a imagem construída.

Para iniciar novamente:

```bash
docker compose up -d
```

Caso o código-fonte tenha sido alterado:

```bash
docker compose up --build -d
```

---

## Utilizando somente Docker

Também é possível construir e executar a aplicação sem Docker Compose.

### Construir a imagem

```bash
docker build -t simulador-banco:1.0 .
```

Verifique se ela foi criada:

```bash
docker image ls
```

### Executar

```bash
docker run --rm \
  --name simulador-banco-api \
  --env-file .env \
  -p 8080:8080 \
  simulador-banco:1.0
```

No PowerShell, o comando pode ser executado em uma única linha:

```powershell
docker run --rm --name simulador-banco-api --env-file .env -p 8080:8080 simulador-banco:1.0
```

A API estará disponível em:

```text
http://localhost:8080
```

O argumento:

```text
--rm
```

faz com que o contêiner seja removido automaticamente quando a aplicação for encerrada.

---

## Exportando a imagem Docker

Caso queira executar a aplicação em outro computador sem precisar reconstruir a imagem, ela pode ser exportada:

```bash
docker save -o simulador-banco-1.0.tar simulador-banco:1.0
```

Transfira o arquivo:

```text
simulador-banco-1.0.tar
```

para o outro computador.

Depois, importe:

```bash
docker load -i simulador-banco-1.0.tar
```

E execute:

```bash
docker run --rm \
  --name simulador-banco-api \
  --env-file .env \
  -p 8080:8080 \
  simulador-banco:1.0
```

O computador de destino precisa possuir Docker instalado e também precisa receber as variáveis de ambiente necessárias.

---

## Armazenamento atual

Nesta versão do projeto, os dados são armazenados em memória.

Isso significa que dados como usuários, carteiras e transações podem ser perdidos quando a aplicação for encerrada ou reiniciada.

A persistência em banco de dados está entre as evoluções planejadas para o projeto.

---

## Próximas implementações

Algumas das melhorias planejadas são:

- [x] Persistência com banco de dados
- [x] PostgreSQL
- [x] Spring Data JPA
- [ ] Chaves Pix
- [ ] Extrato com paginação e filtros
- [ ] Comprovantes de transferência
- [ ] Idempotência em transferências
- [ ] Limites de transferência
- [ ] Rate limiting
- [ ] Testes unitários e de integração
- [ ] Testcontainers
- [ ] Documentação OpenAPI / Swagger
- [ ] Auditoria de operações

---

## Status do projeto

**Em desenvolvimento**

Atualmente, a aplicação possui o fluxo principal de cadastro, autenticação, autorização, consulta de saldo e transferências funcionando.

O projeto continuará evoluindo conforme novos conceitos de back-end, segurança, persistência, testes e infraestrutura forem estudados e implementados.

---

## Finalidade

Este projeto possui finalidade exclusivamente educacional e foi desenvolvido como parte dos meus estudos de desenvolvimento back-end com Java e Spring Boot.
