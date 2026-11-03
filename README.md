
# 🐾 ClyvoCare OS — Backend Java

### Challenge FIAP 2026 | Sprint 4 — Entrega Final

API REST desenvolvida para centralizar informações sobre a saúde dos animais, organizar históricos clínicos e apoiar o cuidado veterinário preventivo.

**Tecnologias:** Java 25 • Spring Boot 4.1.0 • Oracle Database • Spring Security • JWT • Flyway • Docker

**Instituição:** FIAP — Análise e Desenvolvimento de Sistemas

**Versão:** 0.0.1-SNAPSHOT

**API publicada:** https://clyvocaresc.onrender.com

**Repositório:** https://github.com/VitoriaMaglio/ClyvoCareSC

---

## 📌 Sobre o projeto

O **ClyvoCare OS** é uma solução voltada à organização e continuidade do cuidado veterinário.

Atualmente, informações importantes sobre a saúde dos animais podem ficar distribuídas entre clínicas, documentos e registros individuais. Essa fragmentação dificulta o acompanhamento de consultas, vacinas, exames, tratamentos e retornos necessários.

Nossa proposta é centralizar essas informações em uma plataforma digital, permitindo que tutores acompanhem a saúde de seus pets e que profissionais veterinários registrem atendimentos de maneira estruturada.

O projeto é composto por um aplicativo mobile desenvolvido em **React Native com Expo** e uma API REST construída em **Java com Spring Boot**, responsável pelo processamento das regras de negócio, autenticação, persistência e integração com o banco Oracle.

Mais do que armazenar dados, o ClyvoCare busca transformar registros clínicos em informações úteis para o acompanhamento preventivo.

## ✨ Diferenciais da solução

O principal diferencial do ClyvoCare está na automatização de ações preventivas a partir de registros clínicos.

**1. Lembretes automáticos após consultas**

Quando um veterinário conclui uma consulta, o backend registra o diagnóstico, as observações e, quando informado, atualiza o peso do animal. Além disso, cria automaticamente um lembrete de retorno para 15 dias após a data da consulta.

**2. Cálculo de reforços de vacinação**

Ao registrar uma vacina, o sistema valida o item selecionado no catálogo clínico e, quando existe um intervalo de reforço configurado, calcula a data da próxima dose e gera o lembrete correspondente.

**3. Histórico clínico centralizado**

Consultas, exames, tratamentos, prescrições e vacinações ficam vinculados aos registros dos animais, permitindo acompanhar seu histórico de saúde.

**4. Segurança por perfis**

O sistema diferencia tutores, veterinários e administradores de clínicas, aplicando permissões específicas para cada perfil por meio do Spring Security e JWT.

**5. Integração entre mobile e backend**

O aplicativo se comunica com a API publicada no Render por HTTPS, sem acessar diretamente o banco de dados, mantendo a lógica de negócio centralizada no backend.

> O ClyvoCare oferece recursos de apoio ao acompanhamento veterinário e não substitui diagnósticos ou orientações de profissionais habilitados.

---

## ⚙️ Funcionalidades do backend

| Módulo | Funcionalidades |
|---|---|
| Autenticação | Cadastro, login, JWT e criptografia de senhas com BCrypt |
| Tutores | Consulta e atualização de perfil |
| Veterinários | Cadastro e gerenciamento de informações profissionais |
| Pets | Cadastro, consulta, atualização e vínculo com tutores |
| Consultas | Agendamento, histórico, conclusão e cancelamento |
| Exames | Registro e acompanhamento de exames |
| Tratamentos | Registro, atualização e acompanhamento |
| Prescrições | Registro de prescrições vinculadas a tratamentos |
| Vacinação | Registro de vacinas e cálculo de reforços |
| Lembretes | Geração automática e gerenciamento de status |
| Catálogos | Gerenciamento de espécies, cidades, clínicas e itens clínicos |

### Fluxo de consulta e retorno automático

1. O pet é cadastrado pelo tutor.
2. Uma consulta é agendada com status `SCHEDULED`.
3. O veterinário conclui o atendimento.
4. O sistema registra diagnóstico e observações.
5. O status da consulta é alterado para `COMPLETED`.
6. O backend gera automaticamente um lembrete do tipo `APPOINTMENT`.
7. O tutor pode consultar seus lembretes pelo aplicativo.

### Fluxo de vacinação

1. Uma vacina é selecionada no catálogo clínico.
2. O profissional registra sua aplicação.
3. O sistema verifica o intervalo de reforço configurado.
4. A próxima dose é calculada automaticamente, quando aplicável.
5. Um lembrete do tipo `VACCINE` é gerado para acompanhamento.

Esses fluxos demonstram a aplicação de regras de negócio que vão além de operações básicas de cadastro.

---

## 🏗️ Arquitetura da aplicação

O backend utiliza uma **arquitetura em camadas**, separando responsabilidades para facilitar manutenção, organização e evolução do sistema.

```text
ClyvoCare Mobile
React Native + Expo + TypeScript
              |
              | HTTPS / JSON / JWT
              v
      API REST Spring Boot
              |
       Controllers + DTOs
              |
           Services
      (Regras de negócio)
              |
         Repositories
       (Spring Data JPA)
              |
         Hibernate/JDBC
              |
       Oracle Database

Spring Security + JWT
Autenticação e autorização

Flyway
Versionamento do banco
```

### Organização das camadas

- **Controllers:** recebem as requisições HTTP e disponibilizam os endpoints REST.
- **DTOs:** estruturam os dados recebidos e retornados pela API.
- **Services:** implementam validações e regras de negócio.
- **Repositories:** realizam operações de consulta e persistência.
- **Entities:** representam as entidades do domínio e seus relacionamentos.
- **Security:** gerencia autenticação JWT e permissões de acesso.
- **Exception Handling:** centraliza o tratamento de erros da aplicação.

Essa separação permite que a lógica de negócio permaneça independente da interface mobile e da camada de acesso aos dados.

---

## 🛠️ Tecnologias utilizadas

| Tecnologia | Aplicação no projeto |
|---|---|
| Java 25 | Linguagem principal do backend |
| Spring Boot 4.1.0 | Desenvolvimento da API REST |
| Spring Security | Autenticação e autorização |
| JWT | Autenticação baseada em tokens |
| BCrypt | Proteção das senhas dos usuários |
| Spring Data JPA | Persistência e acesso aos dados |
| Hibernate | Mapeamento objeto-relacional |
| Oracle Database | Banco de dados relacional |
| Flyway | Controle de versões do banco |
| Bean Validation | Validação de dados de entrada |
| Gradle | Gerenciamento de dependências e build |
| Docker | Containerização da aplicação |
| Render | Hospedagem do backend |
| Git e GitHub | Versionamento do projeto |
| Postman | Testes automatizados da API |
| Azure Boards | Planejamento, backlog e evidências |

### Decisões técnicas

A escolha do **Java com Spring Boot** permitiu desenvolver uma API organizada, utilizando orientação a objetos, injeção de dependências, validações e separação de responsabilidades.

O **Oracle Database** foi utilizado para garantir persistência relacional e integridade dos dados, enquanto o **Spring Data JPA e Hibernate** facilitaram o relacionamento entre as entidades Java e as tabelas.

O **Flyway** foi adotado para controlar a evolução do banco por meio de migrations SQL. A configuração `spring.jpa.hibernate.ddl-auto=validate` permite validar o schema existente sem depender da criação automática das tabelas pelo Hibernate.

Para segurança, utilizamos **Spring Security, JWT e BCrypt**, protegendo endpoints conforme os perfis dos usuários e evitando o armazenamento de senhas em texto puro.

Por fim, o **Docker e o Render** permitiram disponibilizar o backend online e integrá-lo ao aplicativo mobile.

---

## 🗄️ Banco de dados e modelagem relacional

O projeto utiliza **Oracle Database**, com estrutura organizada em 14 tabelas criadas pelas migrations de V1 a V14.

| Migration | Tabela |
|---|---|
| V1 | USERS |
| V2 | CITIES |
| V3 | SPECIES |
| V4 | OWNERS |
| V5 | PETS |
| V6 | CATALOG_ITEMS |
| V7 | CLINICS |
| V8 | VETERINARIANS |
| V9 | APPOINTMENTS |
| V10 | EXAMS |
| V11 | TREATMENTS |
| V12 | PRESCRIPTIONS |
| V13 | VACCINATIONS |
| V14 | REMINDERS |

As tabelas possuem chaves primárias, estrangeiras e restrições que permitem representar os relacionamentos entre tutores, pets, profissionais e registros clínicos.

Entre os principais relacionamentos estão:

- Tutores associados aos seus pets.
- Pets vinculados às espécies.
- Consultas associadas aos animais, veterinários e clínicas.
- Tratamentos vinculados a prescrições.
- Vacinações relacionadas aos pets e itens do catálogo.
- Lembretes associados aos tutores e animais.

Os scripts SQL estão disponíveis em:

```text
src/main/resources/db/migration/
```

O modelo entidade-relacionamento complementa essa estrutura, apresentando visualmente as entidades, chaves primárias, estrangeiras e cardinalidades.

---

## 🔐 Segurança e autenticação

A API utiliza **Spring Security com autenticação JWT**, permitindo controlar o acesso às funcionalidades de acordo com o perfil do usuário.

### Perfis de acesso

| Perfil | Responsabilidades |
|---|---|
| OWNER | Gerenciamento dos próprios pets e informações do tutor |
| VETERINARIAN | Operações clínicas e registros veterinários |
| CLINIC_ADMIN | Operações administrativas e clínicas autorizadas |

### Funcionamento da autenticação

1. O usuário realiza login com username e senha.
2. O Spring Security valida as credenciais.
3. A API gera um token JWT.
4. O aplicativo utiliza esse token nas requisições protegidas.
5. O backend verifica a autenticação e as permissões necessárias.

Exemplo de cabeçalho:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

A API diferencia respostas como:

- `200 OK`: requisição processada com sucesso.
- `201 Created`: recurso criado.
- `400 Bad Request`: dados inválidos.
- `401 Unauthorized`: autenticação ausente ou inválida.
- `403 Forbidden`: usuário sem permissão.
- `404 Not Found`: recurso não encontrado.
- `409 Conflict`: conflito de dados, quando tratado pela aplicação.

---

## 🌐 Principais endpoints

**Base URL pública:**

```text
https://clyvocaresc.onrender.com
```

### Autenticação

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/auth/register/owner` | Cadastro de tutor |
| POST | `/api/auth/register/veterinarian` | Cadastro de veterinário |
| POST | `/api/auth/login` | Autenticação e geração de JWT |

### Tutores e pets

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/owners/me` | Consultar perfil do tutor |
| PUT | `/api/owners/me` | Atualizar perfil |
| GET | `/api/owners/{ownerId}/pets` | Listar pets |
| POST | `/api/pets` | Cadastrar pet |
| GET | `/api/pets/{id}` | Consultar pet |
| PUT | `/api/pets/{id}` | Atualizar pet |

### Consultas veterinárias

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/appointments` | Agendar consulta |
| GET | `/api/appointments/{id}` | Consultar agendamento |
| GET | `/api/pets/{petId}/appointments` | Histórico de consultas |
| PATCH | `/api/appointments/{id}/complete` | Concluir consulta |
| PATCH | `/api/appointments/{id}/cancel` | Cancelar consulta |

### Vacinação e lembretes

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/pets/{petId}/vaccinations` | Histórico de vacinação |
| POST | `/api/vaccinations` | Registrar vacina |
| GET | `/api/owners/me/reminders` | Consultar lembretes |
| PATCH | `/api/reminders/{id}/sent` | Marcar lembrete como enviado |
| PATCH | `/api/reminders/{id}/confirmed` | Confirmar lembrete |

Também existem endpoints relacionados a exames, tratamentos, prescrições, clínicas, espécies, cidades e catálogo clínico.

---

## 🚀 Deploy e integração com o aplicativo

O backend foi publicado no **Render**, utilizando Docker para padronizar o ambiente de execução.

**API online:**

https://clyvocaresc.onrender.com

O aplicativo mobile utiliza a URL pública para enviar requisições HTTP à API.

### Fluxo da integração

```text
Aplicativo Android
       |
       v
API Spring Boot no Render
       |
       v
Autenticação e regras de negócio
       |
       v
Oracle Database
       |
       v
Resposta JSON para o aplicativo
```

As credenciais do banco e a chave JWT são configuradas por variáveis de ambiente no Render, sem necessidade de incluí-las no código-fonte.

A API não possui uma página inicial pública. Ao acessar diretamente a URL raiz, o Spring Security pode retornar uma mensagem de autenticação necessária. Para testar seu funcionamento, devem ser utilizados os endpoints apropriados.

---

## 💻 Como executar o backend localmente

### Pré-requisitos

- Java JDK 25.
- Git.
- Acesso autorizado ao Oracle Database.
- IntelliJ IDEA ou outro ambiente Java.
- Docker Desktop (opcional).

### 1. Clonar o repositório

```bash
git clone https://github.com/VitoriaMaglio/ClyvoCareSC.git
cd ClyvoCareSC
```

### 2. Configurar variáveis de ambiente

No PowerShell:

```powershell
$env:DB_URL="jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl"
$env:DB_USERNAME="SEU_USUARIO_ORACLE"
$env:DB_PASSWORD="SUA_SENHA_ORACLE"
$env:JWT_SECRET="SUA_CHAVE_JWT_SEGURA"
```

As mesmas variáveis podem ser configuradas no IntelliJ em:

`Run → Edit Configurations → Environment Variables`

### 3. Executar a aplicação

```powershell
.\gradlew.bat bootRun
```

Por padrão, a API estará disponível em:

```text
http://localhost:8080
```

### Execução com Docker

Construir a imagem:

```powershell
docker build -t clyvocare-api .
```

Executar o container:

```powershell
docker run --rm -p 8081:8080 `
  -e DB_URL="$env:DB_URL" `
  -e DB_USERNAME="$env:DB_USERNAME" `
  -e DB_PASSWORD="$env:DB_PASSWORD" `
  -e JWT_SECRET="$env:JWT_SECRET" `
  clyvocare-api
```

Nesse exemplo, o backend estará acessível em:

```text
http://localhost:8081
```

---

## 🧪 Testes e qualidade de software

A qualidade do ClyvoCare foi trabalhada por meio de testes manuais, testes automatizados e validação dos principais fluxos da API.

### Testes manuais — Azure Boards

Foram planejados e documentados **16 casos de teste manuais**, abrangendo funcionalidades essenciais do sistema.

Os testes contemplam autenticação, cadastro, autorização por perfil, operações com pets e validação de respostas esperadas.

Cada cenário apresenta objetivo, dados de entrada, procedimento e resultado esperado, permitindo rastreabilidade das verificações realizadas.

### Testes automatizados — Postman

Foram desenvolvidos **13 cenários automatizados**, executados por meio do Collection Runner.

As verificações incluem:

- Cadastro e login de usuários.
- Autenticação com JWT.
- Validação de permissões.
- Tratamento de dados duplicados.
- Cadastro e consulta de pets.
- Respostas HTTP esperadas.
- Cenários positivos e negativos.

Os cenários executados apresentaram resultados satisfatórios nos testes registrados.

### Testes Java

O projeto também possui configuração para execução de testes pelo Gradle:

```powershell
.\gradlew.bat test
```

Os resultados dos testes Java devem ser consultados no relatório gerado pela execução.

---

## 📚 Integração multidisciplinar

O ClyvoCare integra conhecimentos de diferentes disciplinas do curso de Análise e Desenvolvimento de Sistemas.

### Compliance e gestão de projetos

Utilizamos o **Azure Boards** para organizar o backlog, planejar atividades e registrar evidências do desenvolvimento. A ferramenta auxiliou no acompanhamento das funcionalidades e na documentação dos testes, promovendo organização e rastreabilidade.

### Banco de Dados

Aplicamos conceitos de modelagem relacional, chaves primárias, chaves estrangeiras e integridade referencial por meio do Oracle Database. As migrations SQL com Flyway documentam a criação e evolução das tabelas.

### Engenharia e Testes de Software

Elaboramos casos de teste manuais no Azure Boards e cenários automatizados no Postman, validando funcionalidades, regras de negócio, autenticação e tratamento de erros.

### Desenvolvimento Mobile

A interface React Native/Expo consome os serviços REST desenvolvidos em Java, permitindo que o tutor interaja com os dados de seus pets por meio de uma aplicação mobile.

### Desenvolvimento Backend

O Spring Boot foi utilizado para aplicar orientação a objetos, arquitetura em camadas, persistência, segurança, APIs REST e implementação de regras de negócio contextualizadas com a proposta do projeto.

---

## 📂 Organização do repositório

```text
ClyvoCareSC/
├── src/
│   ├── main/
│   │   ├── java/com/fiap/clyvocaresc/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/
│   └── test/
├── Dockerfile
├── build.gradle
├── gradlew
├── gradlew.bat
└── README.md
```


---

## 👥 Equipe

| Integrante | RM |
|---|---|
| Vitória Valentina Maglio | 563509 |
| Marina Tamagnini Magalhães | 561786 |
| Mateus Granja dos Santos | 564930 |
| Felipe Maglio Filho | 563512 |
| João Pedro Bitencourt Goldoni | 564339 |

**FIAP — Análise e Desenvolvimento de Sistemas**

**Challenge 2026 — Sprint 4**

---

## 🐾 Considerações finais

O ClyvoCare OS reúne conceitos de desenvolvimento backend, segurança, banco de dados, testes e integração mobile em uma solução voltada ao acompanhamento da saúde animal.

O projeto demonstra como o Java e o Spring Boot podem ser utilizados para implementar regras de negócio reais, garantindo organização dos registros, controle de acesso e automatização de processos preventivos.

Seu principal diferencial está em conectar o histórico clínico dos animais a ações futuras de cuidado, como retornos após consultas e reforços de vacinação.

**ClyvoCare OS — Cuidado contínuo para quem faz parte da família.**
