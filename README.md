# 🐾 ClyvoCare OS — Backend Java | Sprint 4

### Challenge FIAP 2026 — Entrega Final

**API REST para continuidade do cuidado veterinário, histórico clínico e prevenção.**

**Versão:** `0.0.1-SNAPSHOT` (backend)  
**Tecnologias principais:** Java 25 · Spring Boot 4.1.0 · Oracle Database · Spring Security · JWT · Flyway  
**Instituição:** FIAP — Análise e Desenvolvimento de Sistemas  
**Repositório backend:** [VitoriaMaglio/ClyvoCareSC](https://github.com/VitoriaMaglio/ClyvoCareSC)  
**Aplicativo mobile:** [ClyvoCare OS — React Native/Expo](https://github.com/marina-2907/clyvocare-mobile-4/tree/feat/sprint4-release)  
**API publicada:** [https://clyvocaresc.onrender.com](https://clyvocaresc.onrender.com)  
**Testes e evidências:** [ClyvoCareSCTests](https://github.com/VitoriaMaglio/ClyvoCareSCTests) · [Azure Boards](https://dev.azure.com/RM563509/ClyvoCareScrum)

> **Aviso:** As informações e os lembretes da plataforma têm caráter de apoio e organização. Não substituem avaliação, orientação ou diagnóstico de um médico-veterinário.

---

## 1. Sobre o projeto — problema e proposta

O acompanhamento da saúde dos animais pode envolver informações espalhadas entre clínicas, documentos e registros individuais. Consultas, vacinas, exames e tratamentos nem sempre ficam reunidos em um histórico acessível, dificultando a continuidade dos cuidados e a identificação de retornos e reforços necessários.

O **ClyvoCare OS** foi desenvolvido para centralizar essas informações e apoiar o cuidado preventivo. A solução conecta o aplicativo Android a uma **API REST em Java**, responsável por autenticar usuários, aplicar regras de negócio, persistir registros clínicos e gerar lembretes a partir de eventos reais do atendimento veterinário.

A proposta não se limita a cadastrar dados: o sistema transforma informações clínicas em ações de acompanhamento, como **recomendar um retorno após uma consulta concluída** e **calcular a próxima dose de uma vacina**.

### Diferenciais da solução

**1. Consulta com retorno automático.** Ao concluir uma consulta, o backend registra diagnóstico e observações, pode atualizar o peso do pet e gera um lembrete de retorno **15 dias após a data da consulta**, vinculado ao tutor e ao animal.

**2. Vacinação com reforço calculado.** Ao registrar uma vacina, a API verifica se o item do catálogo é realmente do tipo `VACCINE`. Quando há um intervalo de reforço configurado, calcula `nextDoseDate = applicationDate + boosterIntervalDays` e cria o lembrete correspondente.

**3. Histórico longitudinal.** Consultas, exames, tratamentos, prescrições e vacinações são associados às entidades do domínio, permitindo acompanhar a trajetória clínica do pet.

**4. Segurança por perfil.** A aplicação diferencia tutores (`OWNER`) de profissionais (`VETERINARIAN` e `CLINIC_ADMIN`), com autenticação JWT e restrições por rota.

**5. Integração mobile e backend independente.** A interface React Native consome a API por HTTPS; o Oracle nunca é acessado diretamente pelo aplicativo.

> O módulo **Clyvo IA**, descrito no aplicativo mobile, apresenta orientações preventivas baseadas em regras e informações do sistema. Não deve ser apresentado como diagnóstico automatizado ou integração comprovada com IA generativa.

---

## 2. Funcionalidades implementadas no backend

| Módulo | Recursos da API |
|---|---|
| Autenticação | Cadastro de tutor e veterinário; login; emissão de JWT; senhas protegidas com BCrypt |
| Tutores e profissionais | Consulta e atualização de perfil; associação com usuário autenticado |
| Pets | Cadastro, consulta, atualização, exclusão e vínculo com tutor e espécie |
| Cadastros de referência | Cidades, espécies, clínicas e itens do catálogo clínico |
| Consultas | Agendamento, consulta por ID, histórico por pet, conclusão e cancelamento |
| Exames | Registro, consulta, atualização e exclusão |
| Tratamentos | Registro, atualização, conclusão e suspensão |
| Prescrições | Registro de prescrições vinculadas a tratamentos |
| Vacinação | Registro, histórico por pet, validação do catálogo e cálculo de reforço |
| Lembretes | Consulta de lembretes do tutor e atualização de status |

### Fluxos de negócio que demonstram o diferencial

**Fluxo A — Consulta e retorno**

```text
Pet cadastrado → Consulta agendada → Atendimento concluído
              → Diagnóstico/observações registrados
              → Reminder APPOINTMENT criado automaticamente
              → Tutor consulta seus lembretes
```

**Fluxo B — Vacinação e reforço**

```text
Pet cadastrado → Vacina selecionada no catálogo
              → Aplicação registrada
              → Próxima dose calculada, se houver intervalo
              → Reminder VACCINE criado automaticamente
```

O lembrete armazenado no Oracle é uma **regra de negócio do backend**. Já a notificação local do Android, descrita no README mobile, é agendada pelo aplicativo com `expo-notifications` para lembrar uma consulta próxima. São mecanismos distintos e complementares.

---

## 3. Arquitetura e decisões técnicas

```text
┌───────────────────────────────────────┐
│ ClyvoCare OS — Android                │
│ React Native + Expo + TypeScript      │
└──────────────────┬────────────────────┘
                   │ HTTPS / JSON / JWT
                   ▼
┌───────────────────────────────────────┐
│ API Java — Spring Boot 4.1.0          │
│ Render + Docker                       │
│                                       │
│ Controllers / DTOs                    │
│          ↓                            │
│ Services — regras de negócio          │
│          ↓                            │
│ Repositories — Spring Data JPA        │
│                                       │
│ Spring Security + filtro JWT          │
└──────────────────┬────────────────────┘
                   │ JDBC / Hibernate
                   ▼
┌───────────────────────────────────────┐
│ Oracle Database — ambiente FIAP       │
│ Estrutura versionada com Flyway       │
└───────────────────────────────────────┘
```

O backend utiliza **arquitetura em camadas**, separando as responsabilidades:

- **Controllers:** expõem os endpoints REST e recebem requisições HTTP.
- **DTOs:** definem os dados de entrada e saída, evitando expor diretamente as entidades JPA.
- **Services:** concentram validações e regras de negócio, como conclusão de consultas e cálculo de reforços.
- **Repositories:** realizam consultas e persistência com Spring Data JPA.
- **Entities e Enums:** representam o domínio clínico e seus estados.
- **Security:** valida tokens JWT, autentica usuários e restringe ações por perfil.
- **Exception handling:** centraliza respostas de erro.

### Tecnologias e justificativas

| Tecnologia | Justificativa |
|---|---|
| **Java 25** | Linguagem principal e tipagem forte para o domínio |
| **Spring Boot 4.1.0** | Estrutura de API REST e integração dos componentes |
| **Spring Security + JWT** | Autenticação sem sessão de servidor e autorização por perfil |
| **BCrypt** | Armazenamento de hashes de senha em vez de senhas em texto puro |
| **Spring Data JPA / Hibernate** | Mapeamento objeto-relacional e acesso ao Oracle |
| **Bean Validation** | Validação de dados recebidos pela API |
| **Oracle Database** | Persistência relacional e integridade referencial |
| **Flyway** | Evolução versionada e rastreável do schema |
| **Gradle** | Gerenciamento de dependências, compilação e testes |
| **Docker + Render** | Empacotamento reproduzível e hospedagem online da API |
| **Git / GitHub** | Versionamento e colaboração |

**Decisão relevante:** `spring.jpa.hibernate.ddl-auto=validate` faz o Hibernate validar a estrutura existente, enquanto as alterações no banco são controladas pelas migrations do Flyway. Essa separação evita depender da geração automática do schema pela aplicação.

---

## 4. Modelagem e persistência — Oracle + Flyway

O banco é composto por **14 tabelas**, definidas nas migrations `V1` a `V14`, com chaves primárias, estrangeiras e restrições de integridade.

| Migration | Tabela |
|---|---|
| `V1` | `USERS` |
| `V2` | `CITIES` |
| `V3` | `SPECIES` |
| `V4` | `OWNERS` |
| `V5` | `PETS` |
| `V6` | `CATALOG_ITEMS` |
| `V7` | `CLINICS` |
| `V8` | `VETERINARIANS` |
| `V9` | `APPOINTMENTS` |
| `V10` | `EXAMS` |
| `V11` | `TREATMENTS` |
| `V12` | `PRESCRIPTIONS` |
| `V13` | `VACCINATIONS` |
| `V14` | `REMINDERS` |

As relações principais permitem associar **tutores a pets**, **pets a espécies e registros clínicos**, **veterinários e clínicas a atendimentos**, **tratamentos a prescrições** e **tutores/pets a lembretes**. O catálogo clínico unifica itens utilizados em processos como vacinação e prescrição.

Os scripts estão em [`src/main/resources/db/migration`](src/main/resources/db/migration). O diagrama MER pode ser anexado à documentação da entrega em `docs/mer-clyvocare.png` (arquivo de evidência a adicionar ao repositório).

> Para demonstrar ao professor: abrir as migrations, mostrar uma FK no SQL e explicar como o relacionamento aparece nas entidades JPA. O modelo visual deve corresponder à versão efetivamente entregue do banco.

---

## 5. Endpoints principais

**Base local:** `http://localhost:8080`  
**Base pública:** `https://clyvocaresc.onrender.com`

| Método | Endpoint | Finalidade |
|---|---|---|
| `POST` | `/api/auth/register/owner` | Cadastrar tutor |
| `POST` | `/api/auth/register/veterinarian` | Cadastrar veterinário |
| `POST` | `/api/auth/login` | Autenticar e obter JWT |
| `GET` / `PUT` | `/api/owners/me` | Consultar / atualizar perfil do tutor |
| `GET` | `/api/species` | Consultar espécies |
| `GET` | `/api/cities` | Consultar cidades |
| `GET` | `/api/owners/{ownerId}/pets` | Listar pets de um tutor |
| `POST` / `PUT` | `/api/pets` / `/api/pets/{id}` | Cadastrar / atualizar pet |
| `GET` | `/api/pets/{id}` | Consultar pet |
| `GET` | `/api/pets/{petId}/appointments` | Histórico de consultas |
| `POST` | `/api/appointments` | Agendar consulta |
| `PATCH` | `/api/appointments/{id}/complete` | Concluir consulta e gerar lembrete |
| `PATCH` | `/api/appointments/{id}/cancel` | Cancelar consulta |
| `GET` | `/api/pets/{petId}/exams` | Histórico de exames |
| `GET` | `/api/pets/{petId}/treatments` | Histórico de tratamentos |
| `GET` | `/api/pets/{petId}/vaccinations` | Histórico vacinal |
| `POST` | `/api/vaccinations` | Registrar vacina e calcular reforço |
| `GET` | `/api/owners/me/reminders` | Consultar lembretes do tutor |
| `PATCH` | `/api/reminders/{id}/sent` | Marcar lembrete como enviado |
| `PATCH` | `/api/reminders/{id}/confirmed` | Marcar lembrete como confirmado |

Outros endpoints de CRUD estão disponíveis nos controllers de `CatalogItem`, `Clinic`, `City`, `Species`, `Exam`, `Treatment` e `Prescription`.

### Segurança e autorização

A autenticação utiliza `Authorization: Bearer <token>`. O Spring Security diferencia:

- **`OWNER`:** perfil do tutor, incluindo operações de cadastro/atualização de pets e acesso a `/api/owners/me`.
- **`VETERINARIAN` / `CLINIC_ADMIN`:** operações clínicas restritas, como registro de vacina, conclusão de consulta e gerenciamento do catálogo.
- **401:** requisição sem autenticação válida em rota protegida.
- **403:** usuário autenticado sem permissão para determinada ação.

**Atenção para a demonstração:** na versão do `SecurityConfig.java` analisada, `POST /api/appointments` exige `VETERINARIAN` ou `CLINIC_ADMIN`. Portanto, o fluxo de agendamento realizado diretamente por um tutor no mobile precisa ser validado com o backend efetivamente publicado antes de ser demonstrado como funcional.

---

## 6. Instalação e execução local

### Pré-requisitos

- JDK **25** e Gradle Wrapper incluído no repositório.
- Acesso autorizado ao Oracle Database.
- IntelliJ IDEA ou terminal com Java configurado.
- Docker Desktop, caso deseje executar em container.

### Clonar o backend

```bash
git clone https://github.com/VitoriaMaglio/ClyvoCareSC.git
cd ClyvoCareSC
```

### Configurar variáveis de ambiente

O `application.properties` lê as credenciais por variáveis, sem armazená-las no GitHub.

**PowerShell:**

```powershell
$env:DB_URL="jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl"
$env:DB_USERNAME="SEU_USUARIO_ORACLE"
$env:DB_PASSWORD="SUA_SENHA_ORACLE"
$env:JWT_SECRET="SUA_CHAVE_JWT_SEGURA"
```

No IntelliJ, também é possível configurar em **Run → Edit Configurations → Environment variables**.

**Executar:**

```powershell
.\gradlew.bat bootRun
```

A API local utiliza `http://localhost:8080`, salvo configuração diferente da variável `PORT`.

### Executar com Docker

```powershell
docker build -t clyvocare-api .
docker run --rm -p 8081:8080 `
  -e DB_URL="$env:DB_URL" `
  -e DB_USERNAME="$env:DB_USERNAME" `
  -e DB_PASSWORD="$env:DB_PASSWORD" `
  -e JWT_SECRET="$env:JWT_SECRET" `
  clyvocare-api
```

Neste exemplo, a API fica acessível em `http://localhost:8081`. O container mantém a porta interna `8080`.

> **Segurança:** nunca publique senhas do Oracle, segredos JWT ou tokens reais no README, em commits, em prints ou no vídeo.

---

## 7. Deploy online — Render

O backend foi preparado com **Dockerfile multi-stage**, utilizando Java 25 para compilação e execução. A aplicação é hospedada como Web Service no Render, com as credenciais e o segredo JWT configurados nas variáveis de ambiente do serviço.

**URL do backend deste repositório:** [https://clyvocaresc.onrender.com](https://clyvocaresc.onrender.com)

A URL raiz (`/`) não é uma página HTML pública: ela pode retornar uma resposta de autenticação necessária, pois o Spring Security protege rotas sem autorização explícita. Para validar a API, utilize os endpoints apropriados, como `POST /api/auth/login`, com dados de teste.

**Observação operacional:** o Oracle acadêmico possui limites de sessões simultâneas. Para reduzir o consumo de conexões, o projeto configura o HikariCP com `DB_POOL_MAX` (padrão `2`) e `DB_POOL_MIN_IDLE` (padrão `0`). Esse ajuste não substitui o monitoramento do limite definido no servidor Oracle.

**Integração com o mobile:** o aplicativo recebe a URL da API por `EXPO_PUBLIC_API_URL`; o frontend comunica-se com o Render via HTTPS e não utiliza credenciais do Oracle.

> O README do mobile registra também uma API em `https://clyvocare-api.onrender.com`. São endereços diferentes: antes da gravação, confirmar qual backend foi utilizado no APK demonstrado e se sua versão corresponde ao código deste repositório.

---

## 8. Testes, qualidade e evidências

O desenvolvimento incluiu **16 casos de testes manuais documentados no Azure Boards** e **13 cenários automatizados no Postman**, executados pelo Collection Runner no trabalho de testes do projeto. As evidências devem ser apresentadas junto aos resultados registrados, distinguindo os testes da API dos testes realizados no aplicativo mobile.

Os cenários contemplam:

- Cadastro e autenticação de tutores e profissionais.
- Validação de dados e tratamento de duplicidades.
- Autorização por perfil e tentativas de acesso indevido.
- Cadastro e consulta de pets.
- Regras de consultas e geração de lembretes.
- Verificação de respostas HTTP, incluindo cenários positivos e negativos.

**Execução de testes Java:**

```powershell
.\gradlew.bat test
```

A aprovação de testes Java deve ser comprovada pelo relatório gerado na execução; os resultados do Postman não substituem o relatório do Gradle.

### Evidências recomendadas para a entrega

| Evidência | O que demonstra |
|---|---|
| Backlog e casos de teste no [Azure Boards](https://dev.azure.com/RM563509/ClyvoCareScrum) | Planejamento, organização, rastreabilidade e Compliance |
| [Repositório de testes](https://github.com/VitoriaMaglio/ClyvoCareSCTests) | Documentação e artefatos de validação |
| Collection Runner do Postman | Execução dos cenários automatizados |
| Migrations `V1`–`V14` e MER | Modelagem relacional e integridade dos dados |
| Repositório GitHub | Versionamento do código e histórico das alterações |
| Render com status Live | Publicação da API online |
| Aplicativo Android em execução | Integração frontend/backend e experiência do usuário |

### Integração multidisciplinar

**Compliance e gestão de qualidade:** o Azure Boards foi utilizado para organizar o backlog, planejar atividades e registrar casos de teste, permitindo relacionar funcionalidades a verificações e evidências. **Banco de Dados:** a modelagem relacional e as migrations SQL demonstram a aplicação de chaves primárias, estrangeiras e restrições de integridade. **Engenharia e Testes de Software:** o Postman, os cenários manuais e as respostas HTTP permitem validar requisitos funcionais e regras de autorização. **Desenvolvimento Mobile:** o frontend React Native/Expo demonstra o consumo da API Java em uma interface voltada ao tutor.

---

## 9. Roteiro de demonstração técnica para o professor

A apresentação deve mostrar **o sistema em funcionamento**, e não apenas slides. Sequência sugerida:

1. **Contexto e proposta:** explicar a dificuldade de centralizar o histórico veterinário e apresentar o ClyvoCare.
2. **Deploy:** mostrar o serviço Live no Render e explicar a comunicação entre Android, API Java e Oracle.
3. **Cadastro e login:** demonstrar a tela mobile, o cadastro de tutor e a autenticação JWT.
4. **Pets e histórico:** cadastrar/consultar um pet e navegar pelas informações disponíveis.
5. **Fluxo clínico:** demonstrar consulta e, pelo Postman, a conclusão do atendimento e a criação automática de lembrete.
6. **Prevenção:** apresentar a regra de vacinação com cálculo de reforço e criação do lembrete.
7. **Banco e segurança:** mostrar uma migration com FK, o modelo relacional e uma rota protegida com retorno 401/403.
8. **Multidisciplinaridade:** apresentar Azure Boards, testes manuais, Postman e evidências de execução.
9. **Encerramento:** destacar o diferencial preventivo, as escolhas técnicas e o impacto esperado.

**UI/UX:** a interface é responsabilidade do aplicativo mobile, mas o backend contribui para a experiência ao fornecer respostas padronizadas, autenticação, validações e dados estruturados. Para o vídeo, mostrar telas reais de login, pets, agenda e alertas.

**Comunicação da equipe:** distribuir os blocos do roteiro entre os cinco integrantes, garantindo que todos falem e demonstrem uma parte do projeto. Gravar com contas de teste, sem expor credenciais ou tokens.

---

## 10. Organização do repositório

```text
ClyvoCareSC/
├── src/
│   ├── main/
│   │   ├── java/com/fiap/clyvocaresc/
│   │   │   ├── controller/
│   │   │   ├── dto/request/
│   │   │   ├── dto/response/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/ (V1 a V14)
│   └── test/
├── Dockerfile
├── build.gradle
├── gradlew
├── gradlew.bat
└── README.md
```

**Para complementar a entrega:** anexar o MER e capturas do Azure Boards, Postman e Render em uma pasta `docs/evidencias/`, mantendo nomes descritivos e sem dados sensíveis.

---

## 11. Equipe

| Integrante | RM |
|---|---|
| Vitória Valentina Maglio | 563509 |
| Marina Tamagnini Magalhães | 561786 |
| Mateus Granja dos Santos | 564930 |
| Felipe Maglio Filho | 563512 |
| João Pedro Bitencourt Goldoni | 564339 |

**FIAP — Análise e Desenvolvimento de Sistemas · Challenge 2026 · Sprint 4**

---

## 12. Considerações finais

O **ClyvoCare OS** reúne desenvolvimento backend, segurança, modelagem relacional, testes e integração mobile em uma solução orientada à continuidade do cuidado veterinário. Seu principal diferencial é a aplicação de **regras preventivas a partir de eventos clínicos reais**, como retornos após consultas e reforços de vacinação, mantendo os registros vinculados ao histórico de cada animal.

O projeto demonstra, de forma contextualizada, conceitos de **Java, orientação a objetos, APIs REST, Spring Boot, Spring Security, JWT, JPA, Oracle, Flyway, Docker, testes e versionamento**, com uma API publicada e integrada à proposta mobile.

**🐾 ClyvoCare OS — Cuidado contínuo para quem faz parte da família.**
