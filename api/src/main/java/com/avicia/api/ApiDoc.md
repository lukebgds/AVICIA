# Documentação da API - AVICIA

**Base URL:** `localhost:9081`

---

## Sumário

- [Autenticação e Sistema](#autenticação-e-sistema)
  - [AuthController](#authcontroller)
  - [LoginController](#logincontroller)
  - [RoleController](#rolecontroller)
  - [SystemLogController](#systemlogcontroller)
- [Usuários](#usuários)
  - [UsuarioController](#usuariocontroller)
- [Paciente](#paciente)
  - [PacienteController](#pacientecontroller)
  - [PacienteUsuarioController](#pacienteusuariocontroller)
- [Dados do Paciente](#dados-do-paciente)
  - [PacienteAlergiaController](#pacientealergiacontroller)
  - [PacienteAnexoController](#pacienteanexocontroller)
  - [PacienteAntecedenteController](#pacienteantecedentecontroller)
  - [PacienteConvenioController](#pacienteconveniocontroller)
  - [PacienteDiagnosticoController](#pacientediagnosticocontroller)
  - [PacienteMedicamentoController](#pacientemedicamentocontroller)
  - [PacienteVacinaController](#pacientevacinacontroller)
- [Profissionais e Funcionários](#profissionais-e-funcionários)
  - [ProfissionalSaudeController](#profissionalsaudecontroller)
  - [FuncionarioController](#funcionariocontroller)
  - [PacienteProfissionalSaudeController](#pacienteprofissionalsaudecontroller)
  - [PacienteFuncionarioController](#pacientefuncionariocontroller)
- [Clínico - Consultas](#clínico---consultas)
  - [ConsultaController](#consultacontroller)
  - [ConsultaDiagnosticoController](#consultadiagnosticocontroller)
  - [ConsultaPrescricaoController](#consultaprescricaocontroller)
  - [PrescricaoItemController](#prescricaoitemcontroller)
- [Clínico - Exames](#clínico---exames)
  - [ExameController](#examecontroller)
  - [ExameSolicitadoController](#examesolicitadocontroller)
  - [ExameResultadoController](#exameresultadocontroller)
- [Clínico - Internações](#clínico---internações)
  - [InternacaoController](#internacaocontroller)
- [Agenda](#agenda)
  - [AgendaController](#agendacontroller)

---

## Autenticação e Sistema

### AuthController

**Base Path:** `/sistema/auth/2fa`

**URL Base:** `localhost:9081/sistema/auth/2fa`

#### Endpoints

##### Enviar Código 2FA
- **Método:** `POST`
- **URL:** `localhost:9081/sistema/auth/2fa/enviar`
- **Permissões:** Público
- **Request Body:** [TwoFactorRequest](#twofactorrequest)
- **Resposta:** Mensagem de confirmação

##### Validar Código 2FA
- **Método:** `POST`
- **URL:** `localhost:9081/sistema/auth/2fa/validar`
- **Permissões:** Público
- **Request Body:** [TwoFactorValidationRequest](#twofactorvalidationrequest)
- **Resposta:** Mensagem de validação

---

### LoginController

**Base Path:** `/api`

**URL Base:** `localhost:9081/api`

#### Endpoints

##### Login de Usuário
- **Método:** `POST`
- **URL:** `localhost:9081/api/login`
- **Permissões:** Público
- **Request Body:** [LoginRequest](#loginrequest)
- **Retorno:** [LoginResponse](#loginresponse)

##### Login de Administrador
- **Método:** `POST`
- **URL:** `localhost:9081/api/admin/login`
- **Permissões:** Público
- **Request Body:** [LoginAdminRequest](#loginadminrequest)
- **Retorno:** [LoginResponse](#loginresponse)

---

### RoleController

**Base Path:** `/api/roles`

**URL Base:** `localhost:9081/api/roles`

#### Endpoints

##### Criar Role
- **Método:** `POST`
- **URL:** `localhost:9081/api/roles`
- **Permissões:** `ROLE_CREATE`
- **Request Body:** [RoleRequest](#rolerequest)
- **Retorno:** [CriarRoleResponse](#criarroleresponse)

##### Listar Todas as Roles
- **Método:** `GET`
- **URL:** `localhost:9081/api/roles`
- **Permissões:** `ROLE_READ`
- **Retorno:** `List<`[RoleResponse](#roleresponse)`>`

##### Buscar Role por Nome
- **Método:** `GET`
- **URL:** `localhost:9081/api/roles/{nome}`
- **Path Variable:** `nome` (String)
- **Permissões:** Público
- **Retorno:** [RoleResponse](#roleresponse)

##### Atualizar Role
- **Método:** `PUT`
- **URL:** `localhost:9081/api/roles/{nome}`
- **Path Variable:** `nome` (String)
- **Permissões:** `ROLE_UPDATE`
- **Request Body:** [RoleRequest](#rolerequest)
- **Retorno:** [RoleResponse](#roleresponse)

##### Deletar Role
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/roles/{nome}`
- **Path Variable:** `nome` (String)
- **Permissões:** `ROLE_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### SystemLogController

**Base Path:** `/api/logs`

**URL Base:** `localhost:9081/api/logs`

#### Endpoints

##### Listar Todos os Logs
- **Método:** `GET`
- **URL:** `localhost:9081/api/logs`
- **Permissões:** `LOG_READ`
- **Retorno:** `List<`[SystemLogResponse](#systemlogresponse)`>`

##### Buscar Log por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/logs/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `LOG_READ`
- **Retorno:** [SystemLogResponse](#systemlogresponse)

##### Buscar Logs por Tipo
- **Método:** `GET`
- **URL:** `localhost:9081/api/logs/tipo/{tipo}`
- **Path Variable:** `tipo` (TipoSystemLog)
- **Permissões:** `LOG_READ`
- **Retorno:** `List<`[SystemLogResponse](#systemlogresponse)`>`

##### Buscar Logs por Usuário
- **Método:** `GET`
- **URL:** `localhost:9081/api/logs/usuario/{idUsuario}`
- **Path Variable:** `idUsuario` (Integer)
- **Permissões:** `LOG_READ`
- **Retorno:** `List<`[SystemLogResponse](#systemlogresponse)`>`

##### Buscar Logs por Entidade
- **Método:** `GET`
- **URL:** `localhost:9081/api/logs/entidade/{entidade}`
- **Path Variable:** `entidade` (String)
- **Permissões:** `LOG_READ`
- **Retorno:** `List<`[SystemLogResponse](#systemlogresponse)`>`

##### Buscar Logs por Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/logs/periodo`
- **Query Params:** `dataInicio`, `dataFim` (LocalDateTime)
- **Permissões:** `LOG_READ`
- **Retorno:** `List<`[SystemLogResponse](#systemlogresponse)`>`

##### Deletar Logs Antigos
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/logs/antigos`
- **Query Param:** `dataLimite` (LocalDateTime)
- **Permissões:** `LOG_DELETE`
- **Retorno:** `String` (mensagem de sucesso)

##### Obter Estatísticas dos Logs
- **Método:** `GET`
- **URL:** `localhost:9081/api/logs/estatisticas`
- **Permissões:** `LOG_READ`
- **Retorno:** [EstatisticasLogsResponse](#estatisticaslogsresponse)

---

## Usuários

### UsuarioController

**Base Path:** `/api/usuarios`

**URL Base:** `localhost:9081/api/usuarios`

#### Endpoints

##### Criar Usuário
- **Método:** `POST`
- **URL:** `localhost:9081/api/usuarios/cadastro`
- **Permissões:** Público
- **Request Body:** [UsuarioRequest](#usuariorequest)
- **Retorno:** [CriarUsuarioResponse](#criarusuarioresponse)

##### Listar Todos os Usuários
- **Método:** `GET`
- **URL:** `localhost:9081/api/usuarios`
- **Permissões:** `USUARIO_READ`
- **Retorno:** `List<`[UsuarioResponse](#usuarioresponse)`>`

##### Buscar Usuário por CPF
- **Método:** `GET`
- **URL:** `localhost:9081/api/usuarios/{cpf}`
- **Path Variable:** `cpf` (String)
- **Permissões:** `USUARIO_READ`
- **Retorno:** [UsuarioResponse](#usuarioresponse)

##### Atualizar Usuário
- **Método:** `PUT`
- **URL:** `localhost:9081/api/usuarios/{cpf}`
- **Path Variable:** `cpf` (String)
- **Permissões:** `USUARIO_UPDATE`
- **Request Body:** [UsuarioRequest](#usuariorequest)
- **Retorno:** [UsuarioResponse](#usuarioresponse)

##### Alterar Senha
- **Método:** `PUT`
- **URL:** `localhost:9081/api/usuarios/{cpf}/alterar-senha`
- **Path Variable:** `cpf` (String)
- **Permissões:** `USUARIO_UPDATE`
- **Request Body:** [AlterarSenhaRequest](#alterarsenharequest)
- **Retorno:** [UsuarioResponse](#usuarioresponse)

##### Deletar Usuário
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/usuarios/{idUsuario}`
- **Path Variable:** `idUsuario` (Integer)
- **Permissões:** `USUARIO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

## Paciente

### PacienteController

**Base Path:** `/api/pacientes`

**URL Base:** `localhost:9081/api/pacientes`

#### Endpoints

##### Listar Todos os Pacientes
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes`
- **Permissões:** `PACIENTE_READ`
- **Retorno:** `List<`[PacienteResponse](#pacienteresponse)`>`

##### Buscar Paciente por CPF
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/{cpf}`
- **Path Variable:** `cpf` (String)
- **Permissões:** `PACIENTE_READ`
- **Retorno:** [PacienteResponse](#pacienteresponse)

##### Criar Paciente
- **Método:** `POST`
- **URL:** `localhost:9081/api/pacientes/cadastro`
- **Permissões:** Público
- **Request Body:** [PacienteRequest](#pacienterequest)
- **Retorno:** [PacienteResponse](#pacienteresponse)

##### Atualizar Paciente
- **Método:** `PUT`
- **URL:** `localhost:9081/api/pacientes/{cpf}`
- **Path Variable:** `cpf` (String)
- **Permissões:** `PACIENTE_UPDATE`
- **Request Body:** [PacienteRequest](#pacienterequest)
- **Retorno:** [PacienteResponse](#pacienteresponse)

##### Deletar Paciente
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/pacientes/{cpf}`
- **Path Variable:** `cpf` (String)
- **Permissões:** `PACIENTE_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### PacienteUsuarioController

**Base Path:** `/api/usuarios-pacientes`

**URL Base:** `localhost:9081/api/usuarios-pacientes`

#### Endpoints

##### Criar Vínculo Usuário-Paciente
- **Método:** `POST`
- **URL:** `localhost:9081/api/usuarios-pacientes/{idUsuario}/{idPaciente}`
- **Path Variables:**
  - `idUsuario` (Integer)
  - `idPaciente` (Integer)
- **Permissões:** Público
- **Retorno:** `ResponseEntity<Void>`

##### Deletar Vínculo Usuário-Paciente
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/usuarios-pacientes/{idUsuario}/{idPaciente}`
- **Path Variables:**
  - `idUsuario` (Integer)
  - `idPaciente` (Integer)
- **Permissões:** Público
- **Retorno:** `ResponseEntity<Void>`

---

## Dados do Paciente

### PacienteAlergiaController

**Base Path:** `/api/pacientes/alergias`

**URL Base:** `localhost:9081/api/pacientes/alergias`

#### Endpoints

##### Listar Todas as Alergias
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/alergias`
- **Permissões:** `ALERGIA_READ`
- **Retorno:** `List<`[PacienteAlergiaResponse](#pacientealergiaresponse)`>`

##### Listar Alergias por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/alergias/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `ALERGIA_READ`
- **Retorno:** `List<`[PacienteAlergiaResponse](#pacientealergiaresponse)`>`

##### Buscar Alergia por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/alergias/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `ALERGIA_READ`
- **Retorno:** [PacienteAlergiaResponse](#pacientealergiaresponse)

##### Criar Alergia
- **Método:** `POST`
- **URL:** `localhost:9081/api/pacientes/alergias`
- **Permissões:** `ALERGIA_CREATE`
- **Request Body:** [PacienteAlergiaRequest](#pacientealergiarequest)
- **Retorno:** [PacienteAlergiaResponse](#pacientealergiaresponse)

##### Atualizar Alergia
- **Método:** `PUT`
- **URL:** `localhost:9081/api/pacientes/alergias/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `ALERGIA_UPDATE`
- **Request Body:** [PacienteAlergiaRequest](#pacientealergiarequest)
- **Retorno:** [PacienteAlergiaResponse](#pacientealergiaresponse)

##### Deletar Alergia
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/pacientes/alergias/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `ALERGIA_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### PacienteAnexoController

**Base Path:** `/api/pacientes/anexos`

**URL Base:** `localhost:9081/api/pacientes/anexos`

#### Endpoints

##### Listar Todos os Anexos
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/anexos`
- **Permissões:** `ANEXO_READ`
- **Retorno:** List<[PacienteAnexoResponse](#pacienteanexoresponse)>

##### Listar Anexos por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/anexos/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `ANEXO_READ`
- **Retorno:** List<[PacienteAnexoResponse](#pacienteanexoresponse)>

##### Listar Anexos por Paciente e Tipo
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/anexos/paciente/{idPaciente}/tipo`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `tipo` (String)
- **Permissões:** `ANEXO_READ`
- **Retorno:** List<[PacienteAnexoResponse](#pacienteanexoresponse)>

##### Buscar Anexo por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/anexos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `ANEXO_READ`
- **Retorno:** [PacienteAnexoResponse](#pacienteanexoresponse)

##### Criar Anexo
- **Método:** `POST`
- **URL:** `localhost:9081/api/pacientes/anexos`
- **Permissões:** `ANEXO_CREATE`
- **Request Body:** [PacienteAnexoRequest](#pacienteanexorequest)
- **Retorno:** [PacienteAnexoResponse](#pacienteanexoresponse)

##### Atualizar Anexo
- **Método:** `PUT`
- **URL:** `localhost:9081/api/pacientes/anexos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `ANEXO_UPDATE`
- **Request Body:** [PacienteAnexoRequest](#pacienteanexorequest)
- **Retorno:** [PacienteAnexoResponse](#pacienteanexoresponse)

##### Deletar Anexo
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/pacientes/anexos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `ANEXO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### PacienteAntecedenteController

**Base Path:** `/api/pacientes/antecedentes`

**URL Base:** `localhost:9081/api/pacientes/antecedentes`

#### Endpoints

##### Listar Todos os Antecedentes
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/antecedentes`
- **Permissões:** `ANTECEDENTE_READ`
- **Retorno:** List<[PacienteAntecedenteResponse](#pacienteantecedenteresponse)>

##### Listar Antecedentes por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/antecedentes/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `ANTECEDENTE_READ`
- **Retorno:** List<[PacienteAntecedenteResponse](#pacienteantecedenteresponse)>

##### Listar Antecedentes por Paciente e Tipo de Doença
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/antecedentes/paciente/{idPaciente}/tipo-doenca`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `tipoDoenca` (String)
- **Permissões:** `ANTECEDENTE_READ`
- **Retorno:** List<[PacienteAntecedenteResponse](#pacienteantecedenteresponse)>

##### Listar Antecedentes por Paciente e Parentesco
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/antecedentes/paciente/{idPaciente}/parentesco`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `parentesco` (String)
- **Permissões:** `ANTECEDENTE_READ`
- **Retorno:**`List<[PacienteAntecedenteResponse](#pacienteantecedenteresponse)>

##### Buscar Antecedente por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/antecedentes/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `ANTECEDENTE_READ`
- **Retorno:** [PacienteAntecedenteResponse](#pacienteantecedenteresponse)

##### Criar Antecedente
- **Método:** `POST`
- **URL:** `localhost:9081/api/pacientes/antecedentes`
- **Permissões:** `ANTECEDENTE_CREATE`
- **Request Body:** [PacienteAntecedenteRequest](#pacienteantecedenterequest)
- **Retorno:** [PacienteAntecedenteResponse](#pacienteantecedenteresponse)

##### Atualizar Antecedente
- **Método:** `PUT`
- **URL:** `localhost:9081/api/pacientes/antecedentes/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `ANTECEDENTE_UPDATE`
- **Request Body:** [PacienteAntecedenteRequest](#pacienteantecedenterequest)
- **Retorno:** [PacienteAntecedenteResponse](#pacienteantecedenteresponse)

##### Deletar Antecedente
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/pacientes/antecedentes/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `ANTECEDENTE_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### PacienteConvenioController

**Base Path:** `/api/pacientes/convenios`

**URL Base:** `localhost:9081/api/pacientes/convenios`

#### Endpoints

##### Listar Todos os Convênios
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/convenios`
- **Permissões:** `CONVENIO_READ`
- **Retorno:** List<[PacienteConvenioResponse](#pacienteconvenioresponse)>

##### Listar Convênios por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/convenios/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `CONVENIO_READ`
- **Retorno:** List<[PacienteConvenioResponse](#pacienteconvenioresponse)>

##### Listar Convênios por Paciente e Nome do Convênio
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/convenios/paciente/{idPaciente}/nome-convenio`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `nomeConvenio` (String)
- **Permissões:** `CONVENIO_READ`
- **Retorno:** List<[PacienteConvenioResponse](#pacienteconvenioresponse)>

##### Listar Convênios Válidos por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/convenios/paciente/{idPaciente}/validos`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `CONVENIO_READ`
- **Retorno:** List<[PacienteConvenioResponse](#pacienteconvenioresponse)>

##### Listar Convênios Vencidos por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/convenios/paciente/{idPaciente}/vencidos`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `CONVENIO_READ`
- **Retorno:** List<[PacienteConvenioResponse](#pacienteconvenioresponse)>

##### Buscar Convênio por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/convenios/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `CONVENIO_READ`
- **Retorno:** [PacienteConvenioResponse](#pacienteconvenioresponse)

##### Criar Convênio
- **Método:** `POST`
- **URL:** `localhost:9081/api/pacientes/convenios`
- **Permissões:** `CONVENIO_CREATE`
- **Request Body:** [PacienteConvenioRequest](#pacienteconveniorequest)
- **Retorno:** [PacienteConvenioResponse](#pacienteconvenioresponse)

##### Atualizar Convênio
- **Método:** `PUT`
- **URL:** `localhost:9081/api/pacientes/convenios/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `CONVENIO_UPDATE`
- **Request Body:** [PacienteConvenioRequest](#pacienteconveniorequest)
- **Retorno:** [PacienteConvenioResponse](#pacienteconvenioresponse)

##### Deletar Convênio
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/pacientes/convenios/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `CONVENIO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### PacienteDiagnosticoController

**Base Path:** `/api/pacientes/diagnosticos`

**URL Base:** `localhost:9081/api/pacientes/diagnosticos`

#### Endpoints

##### Listar Todos os Diagnósticos
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/diagnosticos`
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[PacienteDiagnosticoResponse](#pacientediagnosticoresponse)>

##### Listar Diagnósticos por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/diagnosticos/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[PacienteDiagnosticoResponse](#pacientediagnosticoresponse)>

##### Listar Diagnósticos por Paciente e Código CID
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/diagnosticos/paciente/{idPaciente}/codigo-cid`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `codigoCidDez` (String)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[PacienteDiagnosticoResponse](#pacientediagnosticoresponse)>

##### Listar Diagnósticos por Paciente e Tipo
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/diagnosticos/paciente/{idPaciente}/tipo`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `tipo` (TipoDiagnostico)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[PacienteDiagnosticoResponse](#pacientediagnosticoresponse)>

##### Listar Diagnósticos por Paciente e Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/diagnosticos/paciente/{idPaciente}/status`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `status` (StatusDiagnostico)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[PacienteDiagnosticoResponse](#pacientediagnosticoresponse)>

##### Listar Diagnósticos por Paciente e Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/diagnosticos/paciente/{idPaciente}/periodo`
- **Path Variable:** `idPaciente` (Integer)
- **Query Params:** `dataInicio`, `dataFim` (LocalDate)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[PacienteDiagnosticoResponse](#pacientediagnosticoresponse)>

##### Listar Diagnósticos por Paciente, Tipo e Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/diagnosticos/paciente/{idPaciente}/tipo-status`
- **Path Variable:** `idPaciente` (Integer)
- **Query Params:** `tipo` (TipoDiagnostico), `status` (StatusDiagnostico)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[PacienteDiagnosticoResponse](#pacientediagnosticoresponse)>

##### Buscar Diagnóstico por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/diagnosticos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** [PacienteDiagnosticoResponse](#pacientediagnosticoresponse)

##### Criar Diagnóstico
- **Método:** `POST`
- **URL:** `localhost:9081/api/pacientes/diagnosticos`
- **Permissões:** `DIAGNOSTICO_CREATE`
- **Request Body:** [PacienteDiagnosticoRequest](#pacientediagnosticorequest)
- **Retorno:** [PacienteDiagnosticoResponse](#pacientediagnosticoresponse)

##### Atualizar Diagnóstico
- **Método:** `PUT`
- **URL:** `localhost:9081/api/pacientes/diagnosticos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `DIAGNOSTICO_UPDATE`
- **Request Body:** [PacienteDiagnosticoRequest](#pacientediagnosticorequest)
- **Retorno:** [PacienteDiagnosticoResponse](#pacientediagnosticoresponse)

##### Deletar Diagnóstico
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/pacientes/diagnosticos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `DIAGNOSTICO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### PacienteMedicamentoController

**Base Path:** `/api/pacientes/medicamentos`

**URL Base:** `localhost:9081/api/pacientes/medicamentos`

#### Endpoints

##### Listar Todos os Medicamentos
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/medicamentos`
- **Permissões:** `MEDICAMENTO_READ`
- **Retorno:** List<[PacienteMedicamentoResponse](#pacientemedicamentoresponse)>

##### Listar Medicamentos por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/medicamentos/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `MEDICAMENTO_READ`
- **Retorno:** List<[PacienteMedicamentoResponse](#pacientemedicamentoresponse)>

##### Buscar Medicamento por Nome
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/medicamentos/paciente/{idPaciente}/buscar`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `medicamento` (String)
- **Permissões:** `MEDICAMENTO_READ`
- **Retorno:** List<[PacienteMedicamentoResponse](#pacientemedicamentoresponse)>

##### Listar Medicamentos por Frequência
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/medicamentos/paciente/{idPaciente}/frequencia`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `frequencia` (String)
- **Permissões:** `MEDICAMENTO_READ`
- **Retorno:** List<[PacienteMedicamentoResponse](#pacientemedicamentoresponse)>

##### Buscar Medicamento por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/medicamentos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `MEDICAMENTO_READ`
- **Retorno:** [PacienteMedicamentoResponse](#pacientemedicamentoresponse)

##### Criar Medicamento
- **Método:** `POST`
- **URL:** `localhost:9081/api/pacientes/medicamentos`
- **Permissões:** `MEDICAMENTO_CREATE`
- **Request Body:** [PacienteMedicamentoRequest](#pacientemedicamentorequest)
- **Retorno:** [PacienteMedicamentoResponse](#pacientemedicamentoresponse)

##### Atualizar Medicamento
- **Método:** `PUT`
- **URL:** `localhost:9081/api/pacientes/medicamentos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `MEDICAMENTO_UPDATE`
- **Request Body:** [PacienteMedicamentoRequest](#pacientemedicamentorequest)
- **Retorno:** [PacienteMedicamentoResponse](#pacientemedicamentoresponse)

##### Deletar Medicamento
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/pacientes/medicamentos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `MEDICAMENTO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### PacienteVacinaController

**Base Path:** `/api/pacientes/vacinas`

**URL Base:** `localhost:9081/api/pacientes/vacinas`

#### Endpoints

##### Listar Todas as Vacinas
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/vacinas`
- **Permissões:** `VACINA_READ`
- **Retorno:** List<[PacienteVacinaResponse](#pacientevacinaresponse)>

##### Listar Vacinas por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/vacinas/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `VACINA_READ`
- **Retorno:** List<[PacienteVacinaResponse](#pacientevacinaresponse)>

##### Listar Vacinas por Paciente (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/vacinas/paciente/{idPaciente}/ordenado`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `VACINA_READ`
- **Retorno:** List<[PacienteVacinaResponse](#pacientevacinaresponse)>

##### Buscar Vacinas por Nome
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/vacinas/paciente/{idPaciente}/buscar`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `vacina` (String)
- **Permissões:** `VACINA_READ`
- **Retorno:** List<[PacienteVacinaResponse](#pacientevacinaresponse)>

##### Listar Vacinas por Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/vacinas/paciente/{idPaciente}/periodo`
- **Path Variable:** `idPaciente` (Integer)
- **Query Params:** `dataInicio`, `dataFim` (LocalDateTime)
- **Permissões:** `VACINA_READ`
- **Retorno:** List<[PacienteVacinaResponse](#pacientevacinaresponse)>

##### Listar Vacinas Recentes
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/vacinas/paciente/{idPaciente}/recentes`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `dataReferencia` (LocalDateTime)
- **Permissões:** `VACINA_READ`
- **Retorno:** List<[PacienteVacinaResponse](#pacientevacinaresponse)>

##### Buscar Vacina por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/pacientes/vacinas/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `VACINA_READ`
- **Retorno:** [PacienteVacinaResponse](#pacientevacinaresponse)

##### Criar Vacina
- **Método:** `POST`
- **URL:** `localhost:9081/api/pacientes/vacinas`
- **Permissões:** `VACINA_CREATE`
- **Request Body:** [PacienteVacinaRequest](#pacientevacinarequest)
- **Retorno:** [PacienteVacinaResponse](#pacientevacinaresponse)

##### Atualizar Vacina
- **Método:** `PUT`
- **URL:** `localhost:9081/api/pacientes/vacinas/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `VACINA_UPDATE`
- **Request Body:** [PacienteVacinaRequest](#pacientevacinarequest)
- **Retorno:** [PacienteVacinaResponse](#pacientevacinaresponse)

##### Deletar Vacina
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/pacientes/vacinas/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `VACINA_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

## Profissionais e Funcionários

### ProfissionalSaudeController

**Base Path:** `/api/profissionais-saude`

**URL Base:** `localhost:9081/api/profissionais-saude`

#### Endpoints

##### Criar Profissional de Saúde
- **Método:** `POST`
- **URL:** `localhost:9081/api/profissionais-saude`
- **Permissões:** `PROFISSIONALSAUDE_CREATE`
- **Request Body:** [ProfissionalSaudeRequest](#profissionalsauderequest)
- **Retorno:** [ProfissionalSaudeResponse](#profissionalsauderesponse)

##### Listar Todos os Profissionais
- **Método:** `GET`
- **URL:** `localhost:9081/api/profissionais-saude`
- **Permissões:** `PROFISSIONALSAUDE_READ`
- **Retorno:** List<[ProfissionalSaudeResponse](#profissionalsauderesponse)>

##### Buscar Profissional por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/profissionais-saude/{idProfissional}`
- **Path Variable:** `idProfissional` (Integer)
- **Permissões:** `PROFISSIONALSAUDE_READ`
- **Retorno:** [ProfissionalSaudeResponse](#profissionalsauderesponse)

##### Buscar Profissional por Matrícula
- **Método:** `GET`
- **URL:** `localhost:9081/api/profissionais-saude/matricula/{matricula}`
- **Path Variable:** `matricula` (String)
- **Permissões:** `PROFISSIONALSAUDE_READ`
- **Retorno:** [ProfissionalSaudeResponse](#profissionalsauderesponse)

##### Buscar Profissional por Registro do Conselho
- **Método:** `GET`
- **URL:** `localhost:9081/api/profissionais-saude/registro/{registroConselho}`
- **Path Variable:** `registroConselho` (String)
- **Permissões:** `PROFISSIONALSAUDE_READ`
- **Retorno:** [ProfissionalSaudeResponse](#profissionalsauderesponse)

##### Atualizar Profissional
- **Método:** `PUT`
- **URL:** `localhost:9081/api/profissionais-saude/{matricula}`
- **Path Variable:** `matricula` (String)
- **Permissões:** `PROFISSIONALSAUDE_UPDATE`
- **Request Body:** [ProfissionalSaudeRequest](#profissionalsauderequest)
- **Retorno:** [ProfissionalSaudeResponse](#profissionalsauderesponse)

##### Deletar Profissional
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/profissionais-saude/{matricula}`
- **Path Variable:** `matricula` (String)
- **Permissões:** `PROFISSIONALSAUDE_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### FuncionarioController

**Base Path:** `/api/funcionarios`

**URL Base:** `localhost:9081/api/funcionarios`

#### Endpoints

##### Criar Funcionário
- **Método:** `POST`
- **URL:** `localhost:9081/api/funcionarios`
- **Permissões:** `FUNCIONARIO_CREATE`
- **Request Body:** [FuncionarioRequest](#funcionariorequest)
- **Retorno:** [FuncionarioResponse](#funcionarioresponse)

##### Listar Todos os Funcionários
- **Método:** `GET`
- **URL:** `localhost:9081/api/funcionarios`
- **Permissões:** `FUNCIONARIO_READ`
- **Retorno:** List<[FuncionarioResponse](#funcionarioresponse)>

##### Buscar Funcionário por Matrícula
- **Método:** `GET`
- **URL:** `localhost:9081/api/funcionarios/{matricula}`
- **Path Variable:** `matricula` (String)
- **Permissões:** `FUNCIONARIO_READ`
- **Retorno:** [FuncionarioResponse](#funcionarioresponse)

##### Atualizar Funcionário
- **Método:** `PUT`
- **URL:** `localhost:9081/api/funcionarios/{matricula}`
- **Path Variable:** `matricula` (String)
- **Permissões:** `FUNCIONARIO_UPDATE`
- **Request Body:** [FuncionarioRequest](#funcionariorequest)
- **Retorno:** [FuncionarioResponse](#funcionarioresponse)

##### Deletar Funcionário
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/funcionarios/{matricula}`
- **Path Variable:** `matricula` (String)
- **Permissões:** `FUNCIONARIO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### PacienteProfissionalSaudeController

**Base Path:** `/api/pacientes-profissionais`

**URL Base:** `localhost:9081/api/pacientes-profissionais`

#### Endpoints

##### Criar Vínculo Profissional-Paciente
- **Método:** `POST`
- **URL:** `localhost:9081/api/pacientes-profissionais/{idProfissionalSaude}/{idPaciente}`
- **Path Variables:**
  - `idProfissionalSaude` (Integer)
  - `idPaciente` (Integer)
- **Permissões:** `VINCULO_CREATE`
- **Retorno:** `ResponseEntity<Void>`

##### Deletar Vínculo Profissional-Paciente
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/pacientes-profissionais/{idProfissionalSaude}/{idPaciente}`
- **Path Variables:**
  - `idProfissionalSaude` (Integer)
  - `idPaciente` (Integer)
- **Permissões:** `VINCULO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### PacienteFuncionarioController

**Base Path:** `/api/funcionarios-pacientes`

**URL Base:** `localhost:9081/api/funcionarios-pacientes`

#### Endpoints

##### Criar Vínculo Funcionário-Paciente
- **Método:** `POST`
- **URL:** `localhost:9081/api/funcionarios-pacientes/{idFuncionario}/{idPaciente}`
- **Path Variables:**
  - `idFuncionario` (Integer)
  - `idPaciente` (Integer)
- **Permissões:** Requer autorização
- **Retorno:** `ResponseEntity<Void>`

##### Deletar Vínculo Funcionário-Paciente
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/funcionarios-pacientes/{idFuncionario}/{idPaciente}`
- **Path Variables:**
  - `idFuncionario` (Integer)
  - `idPaciente` (Integer)
- **Permissões:** Requer autorização
- **Retorno:** `ResponseEntity<Void>`

---

## Clínico - Consultas

### ConsultaController

**Base Path:** `/api/consultas`

**URL Base:** `localhost:9081/api/consultas`

#### Endpoints

##### Listar Todas as Consultas
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas`
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Listar Consultas por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Listar Consultas por Paciente (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/paciente/{idPaciente}/ordenado`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Listar Consultas por Profissional
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/profissional/{idProfissionalSaude}`
- **Path Variable:** `idProfissionalSaude` (Integer)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Listar Consultas por Profissional (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/profissional/{idProfissionalSaude}/ordenado`
- **Path Variable:** `idProfissionalSaude` (Integer)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Listar Consultas por Tipo
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/tipo`
- **Query Param:** `tipoConsulta` (TipoConsulta)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Listar Consultas por Paciente e Tipo
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/paciente/{idPaciente}/tipo`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `tipoConsulta` (TipoConsulta)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Listar Consultas por Profissional e Tipo
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/profissional/{idProfissionalSaude}/tipo`
- **Path Variable:** `idProfissionalSaude` (Integer)
- **Query Param:** `tipoConsulta` (TipoConsulta)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Listar Consultas por Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/periodo`
- **Query Params:** `dataInicio`, `dataFim` (LocalDateTime)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Listar Consultas por Paciente e Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/paciente/{idPaciente}/periodo`
- **Path Variable:** `idPaciente` (Integer)
- **Query Params:** `dataInicio`, `dataFim` (LocalDateTime)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Listar Consultas por Profissional e Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/profissional/{idProfissionalSaude}/periodo`
- **Path Variable:** `idProfissionalSaude` (Integer)
- **Query Params:** `dataInicio`, `dataFim` (LocalDateTime)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Buscar Consultas por Local
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/local`
- **Query Param:** `localConsulta` (String)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** List<[ConsultaResponse](#consultaresponse)>

##### Buscar Consulta por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `CONSULTA_READ`
- **Retorno:** [ConsultaResponse](#consultaresponse)

##### Criar Consulta
- **Método:** `POST`
- **URL:** `localhost:9081/api/consultas`
- **Permissões:** `CONSULTA_CREATE`
- **Request Body:** [ConsultaRequest](#consultarequest)
- **Retorno:** [ConsultaResponse](#consultaresponse)

##### Atualizar Consulta
- **Método:** `PUT`
- **URL:** `localhost:9081/api/consultas/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `CONSULTA_UPDATE`
- **Request Body:** [ConsultaRequest](#consultarequest)
- **Retorno:** [ConsultaResponse](#consultaresponse)

##### Deletar Consulta
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/consultas/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `CONSULTA_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### ConsultaDiagnosticoController

**Base Path:** `/api/consultas/diagnosticos`

**URL Base:** `localhost:9081/api/consultas/diagnosticos`

#### Endpoints

##### Listar Todos os Diagnósticos de Consulta
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/diagnosticos`
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[ConsultaDiagnosticoResponse](#consultadiagnosticoresponse)>

##### Listar Diagnósticos por Consulta
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/diagnosticos/consulta/{idConsulta}`
- **Path Variable:** `idConsulta` (Integer)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[ConsultaDiagnosticoResponse](#consultadiagnosticoresponse)>

##### Listar Diagnósticos por Código CID
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/diagnosticos/codigo-cid`
- **Query Param:** `codigoCidDez` (String)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[ConsultaDiagnosticoResponse](#consultadiagnosticoresponse)>

##### Listar Diagnósticos por Consulta e Código CID
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/diagnosticos/consulta/{idConsulta}/codigo-cid`
- **Path Variable:** `idConsulta` (Integer)
- **Query Param:** `codigoCidDez` (String)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[ConsultaDiagnosticoResponse](#consultadiagnosticoresponse)>

##### Buscar Diagnósticos por Descrição
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/diagnosticos/buscar`
- **Query Param:** `descricao` (String)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** List<[ConsultaDiagnosticoResponse](#consultadiagnosticoresponse)>

##### Buscar Diagnóstico por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/diagnosticos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `DIAGNOSTICO_READ`
- **Retorno:** [ConsultaDiagnosticoResponse](#consultadiagnosticoresponse)

##### Criar Diagnóstico
- **Método:** `POST`
- **URL:** `localhost:9081/api/consultas/diagnosticos`
- **Permissões:** `DIAGNOSTICO_CREATE`
- **Request Body:** [ConsultaDiagnosticoRequest](#consultadiagnosticorequest)
- **Retorno:** [ConsultaDiagnosticoResponse](#consultadiagnosticoresponse)

##### Atualizar Diagnóstico
- **Método:** `PUT`
- **URL:** `localhost:9081/api/consultas/diagnosticos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `DIAGNOSTICO_UPDATE`
- **Request Body:** [ConsultaDiagnosticoRequest](#consultadiagnosticorequest)
- **Retorno:** [ConsultaDiagnosticoResponse](#consultadiagnosticoresponse)

##### Deletar Diagnóstico
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/consultas/diagnosticos/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `DIAGNOSTICO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### ConsultaPrescricaoController

**Base Path:** `/api/consultas/prescricoes`

**URL Base:** `localhost:9081/api/consultas/prescricoes`

#### Endpoints

##### Listar Todas as Prescrições
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes`
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[ConsultaPrescricaoResponse](#consultaprescricaoresponse)>

##### Listar Prescrições por Consulta
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/consulta/{idConsulta}`
- **Path Variable:** `idConsulta` (Integer)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[ConsultaPrescricaoResponse](#consultaprescricaoresponse)>

##### Listar Prescrições por Consulta (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/consulta/{idConsulta}/ordenado`
- **Path Variable:** `idConsulta` (Integer)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[ConsultaPrescricaoResponse](#consultaprescricaoresponse)>

##### Listar Prescrições por Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/status`
- **Query Param:** `status` (StatusPrescricao)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[ConsultaPrescricaoResponse](#consultaprescricaoresponse)>

##### Listar Prescrições por Consulta e Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/consulta/{idConsulta}/status`
- **Path Variable:** `idConsulta` (Integer)
- **Query Param:** `status` (StatusPrescricao)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[ConsultaPrescricaoResponse](#consultaprescricaoresponse)>

##### Listar Prescrições por Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/periodo`
- **Query Params:** `dataInicio`, `dataFim` (LocalDate)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[ConsultaPrescricaoResponse](#consultaprescricaoresponse)>

##### Listar Prescrições por Consulta e Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/consulta/{idConsulta}/periodo`
- **Path Variable:** `idConsulta` (Integer)
- **Query Params:** `dataInicio`, `dataFim` (LocalDate)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[ConsultaPrescricaoResponse](#consultaprescricaoresponse)>

##### Listar Prescrições Recentes
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/recentes`
- **Query Param:** `dataReferencia` (LocalDate)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[ConsultaPrescricaoResponse](#consultaprescricaoresponse)>

##### Buscar Prescrição por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** [ConsultaPrescricaoResponse](#consultaprescricaoresponse)

##### Criar Prescrição
- **Método:** `POST`
- **URL:** `localhost:9081/api/consultas/prescricoes`
- **Permissões:** `PRESCRICAO_CREATE`
- **Request Body:** [ConsultaPrescricaoRequest](#consultaprescricaorequest)
- **Retorno:** [ConsultaPrescricaoResponse](#consultaprescricaoresponse)

##### Atualizar Prescrição
- **Método:** `PUT`
- **URL:** `localhost:9081/api/consultas/prescricoes/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `PRESCRICAO_UPDATE`
- **Request Body:** [ConsultaPrescricaoRequest](#consultaprescricaorequest)
- **Retorno:** [ConsultaPrescricaoResponse](#consultaprescricaoresponse)

##### Deletar Prescrição
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/consultas/prescricoes/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `PRESCRICAO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### PrescricaoItemController

**Base Path:** `/api/prescricoes/itens` (inferido)

**URL Base:** `localhost:9081/api/prescricoes/itens`

#### Endpoints

### PrescricaoItemController

**Base Path:** `/api/consultas/prescricoes/itens`

**URL Base:** `localhost:9081/api/consultas/prescricoes/itens`

#### Endpoints

##### Listar Todos os Itens de Prescrição
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/itens`
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[PrescricaoItemResponse](#prescricaoitemresponse)>

##### Listar Itens por Prescrição
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/itens/prescricao/{idPrescricao}`
- **Path Variable:** `idPrescricao` (Integer)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[PrescricaoItemResponse](#prescricaoitemresponse)>

##### Buscar Itens por Medicamento
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/itens/medicamento`
- **Query Param:** `medicamento` (String)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[PrescricaoItemResponse](#prescricaoitemresponse)>

##### Buscar Itens por Prescrição e Medicamento
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/itens/prescricao/{idPrescricao}/medicamento`
- **Path Variable:** `idPrescricao` (Integer)
- **Query Param:** `medicamento` (String)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[PrescricaoItemResponse](#prescricaoitemresponse)>

##### Listar Itens por Frequência
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/itens/frequencia`
- **Query Param:** `frequencia` (String)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[PrescricaoItemResponse](#prescricaoitemresponse)>

##### Listar Itens por Prescrição e Frequência
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/itens/prescricao/{idPrescricao}/frequencia`
- **Path Variable:** `idPrescricao` (Integer)
- **Query Param:** `frequencia` (String)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** List<[PrescricaoItemResponse](#prescricaoitemresponse)>

##### Buscar Item por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/consultas/prescricoes/itens/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `PRESCRICAO_READ`
- **Retorno:** [PrescricaoItemResponse](#prescricaoitemresponse)

##### Criar Item
- **Método:** `POST`
- **URL:** `localhost:9081/api/consultas/prescricoes/itens`
- **Permissões:** `PRESCRICAO_CREATE`
- **Request Body:** [PrescricaoItemRequest](#prescricaoitemrequest)
- **Retorno:** [PrescricaoItemResponse](#prescricaoitemresponse)

##### Atualizar Item
- **Método:** `PUT`
- **URL:** `localhost:9081/api/consultas/prescricoes/itens/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `PRESCRICAO_UPDATE`
- **Request Body:** [PrescricaoItemRequest](#prescricaoitemrequest)
- **Retorno:** [PrescricaoItemResponse](#prescricaoitemresponse)

##### Deletar Item
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/consultas/prescricoes/itens/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `PRESCRICAO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

## Clínico - Exames

### ExameController

**Base Path:** `/api/exames`

**URL Base:** `localhost:9081/api/exames`

#### Endpoints

##### Listar Todos os Exames
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames`
- **Permissões:** `EXAME_READ`
- **Retorno:** List<[ExameResponse](#exameresponse)>

##### Listar Exames Ativos
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/ativos`
- **Permissões:** `EXAME_READ`
- **Retorno:** List<[ExameResponse](#exameresponse)>

##### Listar Exames Ativos (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/ativos/ordenado`
- **Permissões:** `EXAME_READ`
- **Retorno:** List<[ExameResponse](#exameresponse)>

##### Listar Exames por Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/status`
- **Query Param:** `ativo` (Boolean)
- **Permissões:** `EXAME_READ`
- **Retorno:** List<[ExameResponse](#exameresponse)>

##### Buscar Exames por Nome
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/buscar`
- **Query Param:** `nome` (String)
- **Permissões:** `EXAME_READ`
- **Retorno:** List<[ExameResponse](#exameresponse)>

##### Listar Exames por Tipo
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/tipo`
- **Query Param:** `tipo` (String)
- **Permissões:** `EXAME_READ`
- **Retorno:** List<[ExameResponse](#exameresponse)>

##### Listar Exames por Tipo (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/tipo/ordenado`
- **Query Param:** `tipo` (String)
- **Permissões:** `EXAME_READ`
- **Retorno:** List<[ExameResponse](#exameresponse)>

##### Listar Exames por Tipo e Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/tipo-status`
- **Query Params:** `tipo` (String), `ativo` (Boolean)
- **Permissões:** `EXAME_READ`
- **Retorno:** List<[ExameResponse](#exameresponse)>

##### Buscar Exame por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAME_READ`
- **Retorno:** [ExameResponse](#exameresponse)

##### Criar Exame
- **Método:** `POST`
- **URL:** `localhost:9081/api/exames`
- **Permissões:** `EXAME_CREATE`
- **Request Body:** [ExameRequest](#examerequest)
- **Retorno:** [ExameResponse](#exameresponse)

##### Atualizar Exame
- **Método:** `PUT`
- **URL:** `localhost:9081/api/exames/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAME_UPDATE`
- **Request Body:** [ExameRequest](#examerequest)
- **Retorno:** [ExameResponse](#exameresponse)

##### Ativar Exame
- **Método:** `PATCH`
- **URL:** `localhost:9081/api/exames/{id}/ativar`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAME_UPDATE`
- **Retorno:** [ExameResponse](#exameresponse)

##### Desativar Exame
- **Método:** `PATCH`
- **URL:** `localhost:9081/api/exames/{id}/desativar`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAME_UPDATE`
- **Retorno:** [ExameResponse](#exameresponse)

##### Deletar Exame
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/exames/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAME_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### ExameSolicitadoController

**Base Path:** `/api/exames/solicitados`

**URL Base:** `localhost:9081/api/exames/solicitados`

#### Endpoints

##### Listar Todos os Exames Solicitados
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados`
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** List<[ExameSolicitadoResponse](#examesolicitadoresponse)>

##### Listar Exames Solicitados por Consulta
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados/consulta/{idConsulta}`
- **Path Variable:** `idConsulta` (Integer)
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** List<[ExameSolicitadoResponse](#examesolicitadoresponse)>

##### Listar Exames Solicitados por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** List<[ExameSolicitadoResponse](#examesolicitadoresponse)>

##### Listar Exames Solicitados por Paciente (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados/paciente/{idPaciente}/ordenado`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** List<[ExameSolicitadoResponse](#examesolicitadoresponse)>

##### Listar Exames Solicitados por Profissional
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados/profissional/{idProfissional}`
- **Path Variable:** `idProfissional` (Integer)
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** List<[ExameSolicitadoResponse](#examesolicitadoresponse)>

##### Listar Exames Solicitados por Tipo de Exame
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados/tipo-exame/{idExame}`
- **Path Variable:** `idExame` (Integer)
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** List<[ExameSolicitadoResponse](#examesolicitadoresponse)>

##### Listar Exames Solicitados por Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados/status`
- **Query Param:** `status` (String)
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** List<[ExameSolicitadoResponse](#examesolicitadoresponse)>

##### Listar Exames Solicitados por Paciente e Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados/paciente/{idPaciente}/status`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `status` (String)
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** List<[ExameSolicitadoResponse](#examesolicitadoresponse)>

##### Listar Exames Solicitados por Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados/periodo`
- **Query Params:** `dataInicio`, `dataFim` (LocalDate)
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** List<[ExameSolicitadoResponse](#examesolicitadoresponse)>

##### Listar Exames Solicitados por Paciente e Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados/paciente/{idPaciente}/periodo`
- **Path Variable:** `idPaciente` (Integer)
- **Query Params:** `dataInicio`, `dataFim` (LocalDate)
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** List<[ExameSolicitadoResponse](#examesolicitadoresponse)>

##### Buscar Exame Solicitado por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/solicitados/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAMESOLICITADO_READ`
- **Retorno:** [ExameSolicitadoResponse](#examesolicitadoresponse)

##### Criar Exame Solicitado
- **Método:** `POST`
- **URL:** `localhost:9081/api/exames/solicitados`
- **Permissões:** `EXAMESOLICITADO_CREATE`
- **Request Body:** [ExameSolicitadoRequest](#examesolicitadorequest)
- **Retorno:** [ExameSolicitadoResponse](#examesolicitadoresponse)

##### Atualizar Exame Solicitado
- **Método:** `PUT`
- **URL:** `localhost:9081/api/exames/solicitados/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAMESOLICITADO_UPDATE`
- **Request Body:** [ExameSolicitadoRequest](#examesolicitadorequest)
- **Retorno:** [ExameSolicitadoResponse](#examesolicitadoresponse)

##### Atualizar Status do Exame Solicitado
- **Método:** `PATCH`
- **URL:** `localhost:9081/api/exames/solicitados/{id}/status`
- **Path Variable:** `id` (Integer)
- **Query Param:** `status` (String)
- **Permissões:** `EXAMESOLICITADO_UPDATE`
- **Retorno:** [ExameSolicitadoResponse](#examesolicitadoresponse)

##### Deletar Exame Solicitado
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/exames/solicitados/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAMESOLICITADO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

### ExameResultadoController

**Base Path:** `/api/exames/resultados`

**URL Base:** `localhost:9081/api/exames/resultados`

#### Endpoints

##### Listar Todos os Resultados de Exames
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/resultados`
- **Permissões:** `EXAMERESULTADO_READ`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Buscar Resultado por Exame Solicitado
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/resultados/exame-solicitado/{idExameSolicitado}`
- **Path Variable:** `idExameSolicitado` (Integer)
- **Permissões:** `EXAMERESULTADO_READ`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Listar Resultados por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/resultados/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `EXAMERESULTADO_READ`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Listar Resultados por Paciente (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/resultados/paciente/{idPaciente}/ordenado`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `EXAMERESULTADO_READ`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Listar Resultados por Profissional
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/resultados/profissional/{idProfissional}`
- **Path Variable:** `idProfissional` (Integer)
- **Permissões:** `EXAMERESULTADO_READ`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Listar Resultados por Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/resultados/status`
- **Query Param:** `status` (String)
- **Permissões:** `EXAMERESULTADO_READ`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Listar Resultados por Paciente e Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/resultados/paciente/{idPaciente}/status`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `status` (String)
- **Permissões:** `EXAMERESULTADO_READ`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Listar Resultados por Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/resultados/periodo`
- **Query Params:** `dataInicio`, `dataFim` (LocalDate)
- **Permissões:** `EXAMERESULTADO_READ`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Listar Resultados Pendentes de Assinatura
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/resultados/pendentes-assinatura`
- **Permissões:** `EXAMERESULTADO_READ`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Buscar Resultado por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/exames/resultados/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAMERESULTADO_READ`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Criar Resultado de Exame
- **Método:** `POST`
- **URL:** `localhost:9081/api/exames/resultados`
- **Permissões:** `EXAMERESULTADO_CREATE`
- **Request Body:** [ExameResultadoRequest](#exameresultadorequest)
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Atualizar Resultado de Exame
- **Método:** `PUT`
- **URL:** `localhost:9081/api/exames/resultados/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAMERESULTADO_UPDATE`
- **Request Body:** [ExameResultadoRequest](#exameresultadorequest)
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Assinar Resultado de Exame
- **Método:** `PATCH`
- **URL:** `localhost:9081/api/exames/resultados/{id}/assinar`
- **Path Variable:** `id` (Integer)
- **Query Param:** `idProfissional` (Integer)
- **Permissões:** `EXAMERESULTADO_UPDATE`
- **Retorno:** List<[ExameResultadoResponse](#exameresultadoresponse)>

##### Deletar Resultado de Exame
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/exames/resultados/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `EXAMERESULTADO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

## Clínico - Internações

### InternacaoController

**Base Path:** `/api/internacoes`

**URL Base:** `localhost:9081/api/internacoes`

#### Endpoints

##### Listar Todas as Internações
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes`
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** List<[InternacaoResponse](#internacaoresponse)>

##### Listar Internações por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** List<[InternacaoResponse](#internacaoresponse)>

##### Listar Internações por Paciente (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes/paciente/{idPaciente}/ordenado`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** List<[InternacaoResponse](#internacaoresponse)>

##### Listar Internações por Profissional
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes/profissional/{idProfissional}`
- **Path Variable:** `idProfissional` (Integer)
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** List<[InternacaoResponse](#internacaoresponse)>

##### Listar Internações Ativas
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes/ativas`
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** List<[InternacaoResponse](#internacaoresponse)>

##### Listar Internações Ativas por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes/paciente/{idPaciente}/ativas`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** List<[InternacaoResponse](#internacaoresponse)>

##### Listar Internações Finalizadas
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes/finalizadas`
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** List<[InternacaoResponse](#internacaoresponse)>

##### Listar Internações por Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes/periodo`
- **Query Params:** `dataInicio`, `dataFim` (LocalDate)
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** List<[InternacaoResponse](#internacaoresponse)>

##### Buscar Internações por Leito
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes/leito`
- **Query Param:** `leito` (String)
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** List<[InternacaoResponse](#internacaoresponse)>

##### Buscar Leito Ativo
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes/leito/ativo`
- **Query Param:** `leito` (String)
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** List<[InternacaoResponse](#internacaoresponse)>

##### Buscar Internação por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/internacoes/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `INTERNACAO_READ`
- **Retorno:** [InternacaoResponse](#internacaoresponse)

##### Criar Internação
- **Método:** `POST`
- **URL:** `localhost:9081/api/internacoes`
- **Permissões:** `INTERNACAO_CREATE`
- **Request Body:** [InternacaoRequest](#internacaorequest)
- **Retorno:** [InternacaoResponse](#internacaoresponse)

##### Atualizar Internação
- **Método:** `PUT`
- **URL:** `localhost:9081/api/internacoes/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `INTERNACAO_UPDATE`
- **Request Body:** [InternacaoRequest](#internacaorequest)
- **Retorno:** [InternacaoResponse](#internacaoresponse)

##### Registrar Alta
- **Método:** `PATCH`
- **URL:** `localhost:9081/api/internacoes/{id}/alta`
- **Path Variable:** `id` (Integer)
- **Query Param:** `dataAlta` (LocalDate)
- **Permissões:** `INTERNACAO_UPDATE`
- **Retorno:** [InternacaoResponse](#internacaoresponse)

##### Deletar Internação
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/internacoes/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `INTERNACAO_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

## Agenda

### AgendaController

**Base Path:** `/api/agendas`

**URL Base:** `localhost:9081/api/agendas`

#### Endpoints

##### Listar Todas as Agendas
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas`
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas por Profissional de Saúde
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/profissional/{idProfissionalSaude}`
- **Path Variable:** `idProfissionalSaude` (Integer)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas por Profissional de Saúde (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/profissional/{idProfissionalSaude}/ordenado`
- **Path Variable:** `idProfissionalSaude` (Integer)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas Futuras do Profissional
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/profissional/{idProfissionalSaude}/futuras`
- **Path Variable:** `idProfissionalSaude` (Integer)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas por Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/paciente/{idPaciente}`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas por Paciente (Ordenado)
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/paciente/{idPaciente}/ordenado`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas Futuras do Paciente
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/paciente/{idPaciente}/futuras`
- **Path Variable:** `idPaciente` (Integer)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas por Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/status`
- **Query Param:** `status` (StatusAgenda)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas por Profissional e Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/profissional/{idProfissionalSaude}/status`
- **Path Variable:** `idProfissionalSaude` (Integer)
- **Query Param:** `status` (StatusAgenda)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas por Paciente e Status
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/paciente/{idPaciente}/status`
- **Path Variable:** `idPaciente` (Integer)
- **Query Param:** `status` (StatusAgenda)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas por Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/periodo`
- **Query Params:** `dataInicio`, `dataFim` (LocalDateTime)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas por Profissional e Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/profissional/{idProfissionalSaude}/periodo`
- **Path Variable:** `idProfissionalSaude` (Integer)
- **Query Params:** `dataInicio`, `dataFim` (LocalDateTime)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Listar Agendas por Paciente e Período
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/paciente/{idPaciente}/periodo`
- **Path Variable:** `idPaciente` (Integer)
- **Query Params:** `dataInicio`, `dataFim` (LocalDateTime)
- **Permissões:** `AGENDA_READ`
- **Retorno:** List<[AgendaResponse](#agendaresponse)>

##### Buscar Agenda por ID
- **Método:** `GET`
- **URL:** `localhost:9081/api/agendas/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `AGENDA_READ`
- **Retorno:** [AgendaResponse](#agendaresponse)

##### Criar Agenda
- **Método:** `POST`
- **URL:** `localhost:9081/api/agendas`
- **Permissões:** `AGENDA_CREATE`
- **Request Body:** [AgendaRequest](#agendarequest)
- **Retorno:** [AgendaResponse](#agendaresponse)

##### Atualizar Agenda
- **Método:** `PUT`
- **URL:** `localhost:9081/api/agendas/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `AGENDA_UPDATE`
- **Request Body:** [AgendaRequest](#agendarequest)
- **Retorno:** [AgendaResponse](#agendaresponse)

##### Deletar Agenda
- **Método:** `DELETE`
- **URL:** `localhost:9081/api/agendas/{id}`
- **Path Variable:** `id` (Integer)
- **Permissões:** `AGENDA_DELETE`
- **Retorno:** `ResponseEntity<Void>`

---

## Estruturas de DTOs

### Request DTOs

#### AlterarSenhaRequest
```json
{
  "senhaAtual": "string",
  "novaSenha": "string"
}
```

#### AgendaRequest
```json
{
  "idProfissionalSaude": 0,
  "idPaciente": 0,
  "dataHorario": "2025-01-01T00:00:00",
  "status": "AGENDADO"
}
```

#### ConsultaDiagnosticoRequest
```json
{
  "idConsulta": 0,
  "codigoCidDez": "string",
  "descricao": "string"
}
```

#### ConsultaPrescricaoRequest
```json
{
  "idConsulta": 0,
  "dataEmissao": "2025-01-01",
  "observacoes": "string",
  "status": "ATIVA"
}
```

#### ConsultaRequest
```json
{
  "idPaciente": 0,
  "idProfissionalSaude": 0,
  "dataConsulta": "2025-01-01T00:00:00",
  "tipoConsulta": "PRESENCIAL",
  "localConsulta": "string",
  "anamnese": "string",
  "observacoes": "string"
}
```

#### ExameRequest
```json
{
  "nome": "string",
  "descricao": "string",
  "tipo": "string",
  "ativo": true
}
```

#### ExameResultadoRequest
```json
{
  "idExameSolicitado": 0,
  "dataResultado": "2025-01-01",
  "laudo": "string",
  "arquivoResultado": "string",
  "observacoes": "string",
  "idAssinadoPor": 0,
  "status": "string"
}
```

#### ExameSolicitadoRequest
```json
{
  "idConsulta": 0,
  "idPaciente": 0,
  "idExame": 0,
  "idProfissionalSaude": 0,
  "dataSolicitacao": "2025-01-01",
  "observacoes": "string",
  "status": "string"
}
```

#### FuncionarioRequest
```json
{
  "idUsuario": 0,
  "cargo": "string",
  "setor": "string",
  "matricula": "string",
  "observacoes": "string"
}
```

#### InternacaoRequest
```json
{
  "idPaciente": 0,
  "idProfissionalSaude": 0,
  "dataAdmissao": "2025-01-01",
  "dataAlta": "2025-01-01",
  "leito": "string",
  "observacoes": "string"
}
```

#### LoginAdminRequest
```json
{
  "email": "string",
  "senha": "string",
  "codigoAdmin": "string"
}
```

#### LoginRequest
```json
{
  "email": "string",
  "senha": "string"
}
```

#### PacienteAlergiaRequest
```json
{
  "idPaciente": 0,
  "gravidade": "LEVE",
  "descricao": "string"
}
```

#### PacienteAnexoRequest
```json
{
  "idPaciente": 0,
  "tipo": "string",
  "descricao": "string",
  "urlArquivo": "string"
}
```

#### PacienteAntecedenteRequest
```json
{
  "idPaciente": 0,
  "tipoDoenca": "string",
  "parentesco": "string"
}
```

#### PacienteConvenioRequest
```json
{
  "idPaciente": 0,
  "nomeConvenio": "string",
  "numeroCarteirinha": "string",
  "validade": "2025-01-01"
}
```

#### PacienteDiagnosticoRequest
```json
{
  "idPaciente": 0,
  "codigoCidDez": "string",
  "descricao": "string",
  "dataDiagnostico": "2025-01-01",
  "tipo": "PRINCIPAL",
  "status": "ATIVO"
}
```

#### PacienteMedicamentoRequest
```json
{
  "idPaciente": 0,
  "medicamento": "string",
  "dosagem": "string",
  "frequencia": "string"
}
```

#### PacienteRequest
```json
{
  "idUsuario": 0,
  "profissao": "string",
  "preferenciaContato": "string"
}
```

#### PacienteVacinaRequest
```json
{
  "idPaciente": 0,
  "vacina": "string",
  "dataAplicacao": "2025-01-01T00:00:00"
}
```

#### PrescricaoItemRequest
```json
{
  "idPrescricao": 0,
  "medicamento": "string",
  "dosagem": "string",
  "frequencia": "string",
  "duracao": "string"
}
```

#### ProfissionalSaudeRequest
```json
{
  "idUsuario": 0,
  "matricula": "string",
  "conselho": "string",
  "registroConselho": "string",
  "especialidade": "string",
  "cargo": "string",
  "unidade": "string"
}
```

#### RoleRequest
```json
{
  "nome": "string",
  "idTipoRole": 0,
  "descricao": "string",
  "permissoes": {
    "USUARIO_READ": true,
    "USUARIO_CREATE": false
  }
}
```

### SystemLogRequest
```json
{
  "idUsuario": 0,
  "tipoLog": "CRIACAO",
  "acao": "string",
  "dataHora": "2025-01-01T00:00:00",
  "entidadeAfetada": "string",
  "detalhes": "string"
}
```

### TokenRoleRequest
```json
{
  "idRole": 0,
  "nome": "string",
  "idTipoRole": 0,
  "descricao": "string",
  "permissoes": {
    "USUARIO_READ": true,
    "USUARIO_CREATE": false
  }
}
```

### TwoFactorRequest
```json
{
  "email": "string"
}
```

### TwoFactorValidationRequest
```json
{
  "email": "string",
  "codigo": "string"
}
```

#### SystemLogRequest
```json
{
  "idUsuario": 0,
  "tipoLog": "INFO",
  "acao": "string",
  "dataHora": "2025-01-01T00:00:00",
  "entidadeAfetada": "string",
  "detalhes": "string"
}
```

#### TwoFactorRequest
```json
{
  "email": "string"
}
```

#### TwoFactorValidationRequest
```json
{
  "email": "string",
  "codigo": "string"
}
```

#### UsuarioRequest
```json
{
  "nome": "string",
  "cpf": "string",
  "dataNascimento": "2025-01-01",
  "sexo": "string",
  "estadoCivil": "string",
  "email": "string",
  "senha": "string",
  "telefone": "string",
  "endereco": "string",
  "ativo": true,
  "mfaHabilitado": false,
  "dataCriacao": "2025-01-01",
  "idRole": 0
}
```

---

### Response DTOs

#### AgendaResponse
```json
{
  "idAgenda": 0,
  "idProfissionalSaude": 0,
  "idPaciente": 0,
  "dataHorario": "2025-01-01T00:00:00",
  "status": "AGENDADA"
}
```

#### ConsultaDiagnosticoResponse
```json
{
  "idDiagnostico": 0,
  "idConsulta": 0,
  "codigoCidDez": "string",
  "descricao": "string"
}
```

#### ConsultaPrescricaoResponse
```json
{
  "idPrescricao": 0,
  "idConsulta": 0,
  "dataEmissao": "2025-01-01",
  "observacoes": "string",
  "status": "ATIVA"
}
```

#### ConsultaResponse
```json
{
  "idConsulta": 0,
  "idPaciente": 0,
  "idProfissionalSaude": 0,
  "dataConsulta": "2025-01-01T00:00:00",
  "tipoConsulta": "PRESENCIAL",
  "localConsulta": "string",
  "anamnese": "string",
  "observacoes": "string"
}
```

#### CriarRoleResponse
```json
{
  "idRole": 0
}
```

#### CriarUsuarioResponse
```json
{
  "idUsuario": 0
}
```

#### EstatisticasLogsResponse
```json
{
  "totalLogs": 0,
  "totalCriacoes": 0,
  "totalModificacoes": 0,
  "totalExclusoes": 0,
  "totalErros": 0,
  "totalAvisos": 0,
  "usuarioMaisAtivo": "string",
  "idUsuarioMaisAtivo": 0,
  "acoesUsuarioMaisAtivo": 0,
  "entidadeMaisAfetada": "string",
  "operacoesEntidadeMaisAfetada": 0
}
```

#### ExameResponse
```json
{
  "idExame": 0,
  "nome": "string",
  "descricao": "string",
  "tipo": "string",
  "ativo": true
}
```

#### ExameResultadoResponse
```json
{
  "idResultado": 0,
  "idExameSolicitado": 0,
  "nomeExame": "string",
  "dataResultado": "2025-01-01",
  "laudo": "string",
  "arquivoResultado": "string",
  "observacoes": "string",
  "idAssinadoPor": 0,
  "status": "string"
}
```

#### ExameSolicitadoResponse
```json
{
  "idExameSolicitado": 0,
  "idConsulta": 0,
  "idPaciente": 0,
  "idExame": 0,
  "nomeExame": "string",
  "idProfissionalSaude": 0,
  "dataSolicitacao": "2025-01-01",
  "observacoes": "string",
  "status": "string"
}
```

#### FuncionarioResponse
```json
{
  "idFuncionario": 0,
  "usuario": {
    "idUsuario": 0,
    "nome": "string",
    "cpf": "string",
    "email": "string"
  },
  "cargo": "string",
  "setor": "string",
  "matricula": "string",
  "observacoes": "string"
}
```

#### InternacaoResponse
```json
{
  "idInternacao": 0,
  "idPaciente": 0,
  "idProfissionalSaude": 0,
  "dataAdmissao": "2025-01-01",
  "dataAlta": "2025-01-01",
  "leito": "string",
  "observacoes": "string"
}
```

#### LoginResponse
```json
{
  "token": "string",
  "tipo": "Bearer",
  "usuario": {
    "idUsuario": 0,
    "nome": "string",
    "email": "string"
  }
}
```

#### PacienteAlergiaResponse
```json
{
  "idAlergia": 0,
  "idPaciente": 0,
  "gravidade": "LEVE",
  "descricao": "string"
}
```

#### PacienteAnexoResponse
```json
{
  "idAnexo": 0,
  "idPaciente": 0,
  "tipo": "string",
  "descricao": "string",
  "urlArquivo": "string",
  "dataUpload": "2025-01-01T00:00:00"
}
```

#### PacienteAntecedenteResponse
```json
{
  "idAntecedente": 0,
  "idPaciente": 0,
  "tipoDoenca": "string",
  "parentesco": "string"
}
```

#### PacienteConvenioResponse
```json
{
  "idConvenio": 0,
  "idPaciente": 0,
  "nomeConvenio": "string",
  "numeroCarteirinha": "string",
  "validade": "2025-01-01"
}
```

#### PacienteDiagnosticoResponse
```json
{
  "idDiagnostico": 0,
  "idPaciente": 0,
  "codigoCidDez": "string",
  "descricao": "string",
  "dataDiagnostico": "2025-01-01",
  "tipo": "PRINCIPAL",
  "status": "ATIVO"
}
```

#### PacienteMedicamentoResponse
```json
{
  "idMedicamento": 0,
  "idPaciente": 0,
  "medicamento": "string",
  "dosagem": "string",
  "frequencia": "string"
}
```

#### PacienteResponse
```json
{
  "idPaciente": 0,
  "usuario": {
    "idUsuario": 0,
    "nome": "string",
    "cpf": "string",
    "email": "string"
  },
  "profissao": "string",
  "preferenciaContato": "string"
}
```

#### PacienteVacinaResponse
```json
{
  "idVacina": 0,
  "idPaciente": 0,
  "vacina": "string",
  "dataAplicacao": "2025-01-01T00:00:00"
}
```

#### PrescricaoItemResponse
```json
{
  "idItem": 0,
  "idPrescricao": 0,
  "medicamento": "string",
  "dosagem": "string",
  "frequencia": "string",
  "duracao": "string"
}
```

#### ProfissionalSaudeResponse
```json
{
  "idProfissional": 0,
  "usuario": {
    "idUsuario": 0,
    "nome": "string",
    "cpf": "string",
    "email": "string"
  },
  "matricula": "string",
  "conselho": "string",
  "registroConselho": "string",
  "especialidade": "string",
  "cargo": "string",
  "unidade": "string"
}
```

#### RoleResponse
```json
{
  "idRole": 0,
  "nome": "string",
  "descricao": "string"
}
```

#### SystemLogResponse
```json
{
  "idLog": 0,
  "idUsuario": 0,
  "tipoLog": "INFO",
  "acao": "string",
  "dataHora": "2025-01-01T00:00:00",
  "entidadeAfetada": "string",
  "detalhes": "string"
}
```

#### UsuarioResponse
```json
{
  "idUsuario": 0,
  "nome": "string",
  "cpf": "string",
  "dataNascimento": "2025-01-01",
  "sexo": "string",
  "estadoCivil": "string",
  "email": "string",
  "telefone": "string",
  "endereco": "string",
  "ativo": true,
  "mfaHabilitado": false,
  "dataCriacao": "2025-01-01",
  "idRole": 0
}
```

---

## Enumerações

### Gravidade
- `LEVE` - "leve"
- `MODERADA` - "moderada"
- `GRAVE` - "grave"

### PreferenciaContato
- `TELEFONE` - "telefone"
- `EMAIL` - "email"
- `SMS` - "sms"

### Sexo
- `M` - "m"
- `F` - "f"
- `OUTRO` - "outro"

### StatusAgenda
- `LIVRE` - "livre"
- `AGENDADO` - "agendado"
- `CONCLUIDO` - "concluido"
- `CANCELADO` - "cancelado"
- `BLOQUEADO` - "bloqueado"
- `EXPIRADO` - "expirado"

### StatusDiagnostico
- `ATIVO` - "ativo"
- `CURADO` - "curado"
- `INATIVO` - "inativo"

### StatusPrescricao
- `ATIVA` - "ativa"
- `SUSPENSA` - "suspensa"
- `CONCLUIDA` - "concluida"

### TipoConsulta
- `PRESENCIAL` - "presencial"
- `TELECONSULTA` - "teleconsulta"

### TipoDiagnostico
- `PRINCIPAL` - "principal"
- `SECUNDARIO` - "secundario"

### TipoSystemLog
- `MODIFICACAO` - "MODIFICAÇÃO"
- `CRIACAO` - "CRIAÇÃO"
- `EXCLUSAO` - "EXCLUSÃO"
- `AVISO` - "AVISO"
- `ERRO` - "ERRO"

---

## Notas Importantes

1. **Autenticação**: A maioria dos endpoints requer autenticação via token JWT no header `Authorization: Bearer {token}`

2. **Permissões**: As permissões são controladas por Roles e devem ser configuradas adequadamente

3. **Formatos de Data**:
   - `LocalDate`: formato ISO (yyyy-MM-dd)
   - `LocalDateTime`: formato ISO (yyyy-MM-ddTHH:mm:ss)

4. **Paginação**: Alguns endpoints podem retornar grandes volumes de dados. Considere implementar paginação quando necessário

5. **Paginação**: Alguns endpoints podem retornar grandes volumes de dados. Considere implementar paginação quando necessário

6. **Validações**: Todos os campos marcados como obrigatórios devem ser fornecidos nas requisições

7. **Enumerações**: Ao enviar dados com enumerações, use os valores em MAIÚSCULAS (ex: `AGENDADO`, `PRESENCIAL`, `ATIVO`)

8. **Códigos de Status HTTP**:
   - `200 OK`: Operação bem-sucedida
   - `201 Created`: Recurso criado com sucesso
   - `204 No Content`: Operação bem-sucedida sem conteúdo de retorno
   - `400 Bad Request`: Dados inválidos na requisição
   - `401 Unauthorized`: Não autenticado
   - `403 Forbidden`: Sem permissão para acessar o recurso
   - `404 Not Found`: Recurso não encontrado
   - `500 Internal Server Error`: Erro interno do servidor